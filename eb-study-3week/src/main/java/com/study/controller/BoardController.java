package com.study.controller;

import com.study.model.BoardDTO;
import com.study.model.FileDTO;
import com.study.model.ReplyDTO;
import com.study.service.BoardService;
import com.study.service.FileService;
import com.study.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.List;

/*
 todo :  requestParameter 정리.
 */

@Controller
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;
    private final FileService fileService;

    @RequestMapping("/")
    public String home(){
        return "redirect:/index";
    }

    /**
     * 리스트보기, 카운팅, 페이징, 검색
     * @param pageNum
     * @param pageSize
     * @param searchId
     * @param category
     * @param startDateStr
     * @param endDateStr
     * @param model
     * @return
     *
     * todo: paging 화면에서 조정
     */
    @GetMapping("/index")
    public String boardContentList (@RequestParam(defaultValue = "1") int pageNum,
                                    @RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(value = "searchId", required = false) String searchId,
                                    @RequestParam(value = "category", required = false) String category,
                                    @RequestParam(value = "startDate", required = false) String startDateStr,
                                    @RequestParam(value = "endDate", required = false) String endDateStr,
                                    @ModelAttribute BoardDTO boardDTO,
                                    Model model) {

        int categoryNum = 0;
        Timestamp startDate = null;
        Timestamp endDate = null;

        if(category != null && !category.isEmpty()){
            categoryNum = Integer.parseInt(category);
        }
        if(startDateStr != null && !startDateStr.isEmpty()){
            startDate = Timestamp.valueOf(startDateStr + " 00:00:00");
        }
        if(endDateStr != null && !endDateStr.isEmpty()){
            endDate = Timestamp.valueOf(endDateStr + " 23:59:59");
        }

        List<BoardDTO> boardList = boardService.getBoardList(searchId, categoryNum, startDate, endDate, pageNum, pageSize);
        model.addAttribute("boardList", boardList);

        //counting
        int count = boardService.getBoardCount(searchId, categoryNum, startDate, endDate);
        model.addAttribute("count", count);

        //paging
        int totalPage = (int) Math.ceil((double) count / pageSize);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("pageNum", pageNum);

        return "/index";
    }

    /**
     * content보기, 파일보기, 댓긋보기
     * @param boardNum
     * @param model
     * @return
     */
    @GetMapping("/content")
    public String viewBoardContent (@RequestParam("boardNum") int boardNum,
                                    Model model) {
        try {

            //contentView
            BoardDTO viewBoardContent = boardService.viewBoardContent(boardNum);
            boardService.updateHit(boardNum);
            model.addAttribute("viewBoardContent", viewBoardContent);

            //replyView
            List<ReplyDTO> viewReplyContent = replyService.getReplyList(boardNum);
            model.addAttribute("viewReplyContent", viewReplyContent);

            //fileView
            List<FileDTO> viewFileList = fileService.getFileList(boardNum);
            model.addAttribute("viewFileList", viewFileList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/content";
    }

    /**
     * 댓글 작성
     * @param boardNum
     * @param replyContent
     * @return
     */
    @PostMapping("/content")
    public String writeReplyContent(@RequestParam("boardNum") int boardNum,
                                    @RequestParam("replyContent") String replyContent){

        try {

            if(replyContent != null && !replyContent.isEmpty()){
                replyService.writeReply(boardNum,replyContent);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/content?boardNum="+boardNum;
    }

    /**
     * 글작성, 파일등록
     * @param
     * @param
     * @return
     */

    @GetMapping("/write")
    public String writeBoardContent(){
        return "write";
    }

    @PostMapping("/write")
    public String writeBoardContent (@RequestParam("categoryNum") int categoryNum,
                                     @RequestParam("writer") String writer,
                                     @RequestParam("pw") String pw,
                                     @RequestParam("title") String title,
                                     @RequestParam("content") String content,
                                     HttpServletRequest request,
                                     BoardDTO boardDTO,
                                     MultipartFile file){

        try {

            boardDTO.setCategoryNum(categoryNum);
            boardDTO.setWriter(writer);
            boardDTO.setPw(pw);
            boardDTO.setTitle(title);
            boardDTO.setContent(content);

            if (boardDTO.getPw().equals(pw)) {
                int result = boardService.writeBoardContent(boardDTO);
                if (result > 0) {

                    //file save
                    if (file != null && !file.isEmpty()) {

                        String filePath = "D:\\tmp";

                        fileUpdateList(boardDTO.getBoardNum(), filePath, request.getPart("file"));
                        fileUpdateList(boardDTO.getBoardNum(), filePath, request.getPart("file1"));
                        fileUpdateList(boardDTO.getBoardNum(), filePath, request.getPart("file2"));

                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/index";
    }

    /**
     * 글수정, 파일등록
     * @param boardNum
     * @param
     * @return
     */
    @GetMapping("/modify")
    public String modifyBoardContent (@RequestParam("boardNum") int boardNum,
                                      Model model){
        //boardList
        BoardDTO modifyBoardContent = boardService.viewBoardContent(boardNum);
        model.addAttribute("modifyBoardContent", modifyBoardContent);

        //fileView
        List<FileDTO> viewFileList = fileService.getFileList(boardNum);
        model.addAttribute("viewFileList", viewFileList);
        return "/modify";
    }

    @PostMapping("/modify")
    public String modifyBoardContent (@ModelAttribute BoardDTO boardDTO,
                                      HttpServletRequest request,
                                      MultipartFile file) {

        try {

            if(!boardDTO.getWriter().isEmpty() || !boardDTO.getPw().isEmpty() ||!boardDTO.getContent().isEmpty()) {

                int result = boardService.modifyBoardContent(boardDTO);

                if(result > 0){
                    //file save
                    if (file != null && !file.isEmpty()) {

                        String filePath = "D:\\tmp";

                        fileUpdateList(boardDTO.getBoardNum(), filePath, request.getPart("file"));
                        fileUpdateList(boardDTO.getBoardNum(), filePath, request.getPart("file1"));
                        fileUpdateList(boardDTO.getBoardNum(), filePath, request.getPart("file2"));

                    }
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/content?boardNum="+boardDTO.getBoardNum();
    }

    /**
     * 글삭제
     * @param boardNum
     * @return
     */
    @GetMapping("/delete")
    public String deleteBoardContent(@RequestParam("boardNum") int boardNum,
                                     Model model){
        BoardDTO deleteContent = boardService.viewBoardContent(boardNum);
        model.addAttribute("deleteContent", deleteContent);
        return "/delete";
    }

    @PostMapping("/delete")
    public String deleteBoardContent (@RequestParam("boardNum") int boardNum,
                                      @RequestParam("pw") String pw,
                                      BoardDTO boardDTO) {
        try {

            if(pw != null && !pw.isEmpty()) {
                if (boardDTO.getPw().equals(pw)) {
                        replyService.deleteReply(boardNum);
                        fileService.deleteAllFile(boardNum);
                        boardService.deleteBoardContent(boardNum);
                } else {

                    return "redirect:/delete?boardNum" + boardNum;
                }
            }else{

                return "redirect:/delete?boardNum" + boardNum;
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/index";
    }

    /**
     * file download
     * @param boardNum
     * @param response
     * @throws FileNotFoundException
     */
    @GetMapping("/download")
    public void fileDownload(@RequestParam("boardNum") int boardNum,
                             @RequestParam("fileNum") int fileNum,
                             HttpServletResponse response) {

        try {
            FileDTO fileDTO = fileService.getFileNum(boardNum, fileNum);
            String filePath = fileDTO.getFilePath();

            if(!filePath.startsWith("D:\\tmp")){
                throw new SecurityException("저장된 위치가 아닙니다.");
            }

            File fileDownload = new File(filePath);

            if(!fileDownload.exists() || !fileDownload.isFile()){
                throw new FileNotFoundException("파일이 존재하지 않습니다." + filePath);
            }

            String fileName = fileDownload.getName();

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

            Files.copy(fileDownload.toPath(), response.getOutputStream());

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * file select delete
     * @param boardNum
     * @param fileNum
     * @return
     */

    @GetMapping("/fileDelete")
    public String deleteSelectFile(@RequestParam("boardNum") int boardNum,
                                   @RequestParam("fileNum") int fileNum){

        fileService.deleteSelectFile(boardNum,fileNum);

        return "redirect:/modify?boardNum="+boardNum;
    }

    /**
     * fileUpload method
     * @param boardNum
     * @param filePath
     * @param filePart
     * @throws IOException
     * todo : file upload 오리지널 업로드 이름을 같이 올릴수 있도록, 메소드를 service로 옮겨서 사용 가능하도록
     */
    private void fileUpdateList(int boardNum, String filePath, Part filePart) throws IOException {

        try {
            if(filePart != null && filePart.getSize() > 0) {

                String fileName = filePart.getSubmittedFileName();

                File fileSaveDir = new File(filePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdirs();
                }

                filePart.write(filePath + File.separator + fileName);
                String filePathDB = filePath + File.separator + fileName;

                FileDTO fileDTO = new FileDTO();

                fileDTO.setBoardNum(boardNum);
                fileDTO.setFileName(fileName);
                fileDTO.setFilePath(filePathDB);

                fileService.updateFileList(fileDTO);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
