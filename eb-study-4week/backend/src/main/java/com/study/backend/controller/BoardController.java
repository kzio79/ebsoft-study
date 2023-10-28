package com.study.backend.controller;

import com.study.backend.model.BoardDTO;
import com.study.backend.model.FileDTO;
import com.study.backend.model.ReplyDTO;
import com.study.backend.service.FileService;
import com.study.backend.service.BoardService;
import com.study.backend.service.ReplyService;
import com.study.backend.utils.CustomResponse;
import com.study.backend.utils.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;
    private final FileService fileService;

    @Value("${spring.servlet.multipart.location}")
    private String saveFileUrl;


//    /**
//     * home접속시 index로 이동
//     */
//    @RequestMapping("/")
//    public RedirectView home() {
//        return new RedirectView("/api/v1/index");
//    }

    /**
     * 글 목록 보기
     *
     * @param boardDTO
     * @return
     */
    @GetMapping("/api/v1/index")
    public ResponseEntity<Map<String, Object>> boardContentList(@ModelAttribute BoardDTO boardDTO) {

        int categoryNum = boardDTO.getCategoryNum();
        String searchId = boardDTO.getSearchId();
        Timestamp startDate = null;
        Timestamp endDate = null;

        if (boardDTO.getStartDate() != null && !boardDTO.getStartDate().isEmpty()) {
            startDate = Timestamp.valueOf(boardDTO.getStartDate() + " 00:00:00");
        }
        if (boardDTO.getEndDate() != null && !boardDTO.getEndDate().isEmpty()) {
            endDate = Timestamp.valueOf(boardDTO.getEndDate() + " 23:59:59");
        }

        //boardList
        List<BoardDTO> boardList = boardService.getBoardList(searchId, categoryNum, startDate, endDate, boardDTO.getPageNum(), boardDTO.getPageSize());

        //counting
        int totalCount = boardService.getBoardCount(searchId, categoryNum, startDate, endDate);

        Map<String, Object> response = new HashMap<>();
        response.put("boardList", boardList);
        response.put("totalCount", totalCount);

        return ResponseEntity.ok().body(response);
    }

    /**
     * content보기, 파일보기, 댓긋보기
     *
     * @param boardNum
     * @return
     */
    @GetMapping("/api/v1/content")
    public ResponseEntity<?> viewBoardContent(@Valid @RequestParam("boardNum") int boardNum) {

        //contentView
        BoardDTO viewBoardContent = boardService.viewBoardContent(boardNum);
        boardService.updateHit(boardNum);

        //replyView
        List<ReplyDTO> viewReplyContent = replyService.getReplyList(boardNum);

        //fileView
        List<FileDTO> viewListFile = fileService.getFileList(boardNum);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewBoardContent", viewBoardContent);
        responseMap.put("viewReplyContent", viewReplyContent);
        responseMap.put("viewListFile", viewListFile);

        CustomResponse response = new CustomResponse(ResultCode.SUCCESS, responseMap);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 댓글 작성
     *
     * @param replyDTO
     * @return
     */
    @PostMapping("/api/v1/content")
    public ResponseEntity<?> writeReplyContent(@Valid @RequestBody ReplyDTO replyDTO) {

        Map<String, Object> responseMap = new HashMap<>();

        if (replyDTO.getContent() != null && !replyDTO.getContent().isEmpty()) {
            replyService.writeReply(replyDTO.getBoardNum(), replyDTO.getContent());
            responseMap.put("status", "success");

        } else {

            responseMap.put("status", "내용 보기 실패");
            CustomResponse response = new CustomResponse(ResultCode.BAD_REQUEST, responseMap);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        CustomResponse response = new CustomResponse(ResultCode.SUCCESS, responseMap);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 글 작성
     *
     * @param boardDTO
     * @return
     */

    /*
    todo : 글 작성시 파일을 따로 등록이 아닌 동시에 등록이 되도록 고민....
     */
    @PostMapping("/api/v1/write")
    public ResponseEntity<?> writeBoardContent(@Valid @RequestBody BoardDTO boardDTO) {

        Map<String, Object> responseMap = new HashMap<>();

        boardService.writeBoardContent(boardDTO);
        responseMap.put("boardNum", boardDTO.getBoardNum());

        CustomResponse response = new CustomResponse(ResultCode.SUCCESS, responseMap);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 글 수정
     *
     * @param boardDTO
     * @return
     */
    @PostMapping("/api/v1/modify")
    public ResponseEntity<?> modifyBoardContent(@Valid @RequestBody BoardDTO boardDTO) {

        Map<String, Object> responseMap = new HashMap<>();

        if (boardDTO == null) {
            responseMap.put("error", "데이터가 존재 하지 않습니다.");
            CustomResponse response = new CustomResponse(ResultCode.BAD_REQUEST, responseMap);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        int modifyContent = boardService.modifyBoardContent(boardDTO);

        if (modifyContent == 0) {
            responseMap.put("error", "수정할 데이터가 없습니다.");
            CustomResponse response = new CustomResponse(ResultCode.INTERNAL_SERVER_ERROR, responseMap);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        responseMap.put("modifyContent", modifyContent);

        CustomResponse response = new CustomResponse(ResultCode.SUCCESS, responseMap);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 글 삭제
     *
     * @param boardDTO
     * @return
     */
    @PostMapping("/api/v1/delete")
    public ResponseEntity<?> deleteBoardContent(@Valid @RequestBody BoardDTO boardDTO
    ) {
        Map<String, Object> responseMap = new HashMap<>();

        String validPwCheck = String.valueOf(boardService.validPwCheck(boardDTO.getBoardNum()));

        if (boardDTO.getPw().equals(validPwCheck)) {

            fileService.deleteAllFile(boardDTO.getBoardNum());
            replyService.deleteReply(boardDTO.getBoardNum());
            boardService.deleteBoardContent(boardDTO.getBoardNum());

            responseMap.put("status", "삭제 성공");
            CustomResponse response = new CustomResponse(ResultCode.SUCCESS, responseMap);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {

            responseMap.put("status", "삭제 실패");
            CustomResponse response = new CustomResponse(ResultCode.INTERNAL_SERVER_ERROR, responseMap);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 파일 업데이트
     *
     * @param files
     * @param boardNum
     * @return
     */

    @PostMapping("/insertFile")
    public ResponseEntity<?> updateFile(@Valid @RequestParam("files") List<MultipartFile> files,
                                        @Valid @RequestParam("boardNum") Integer boardNum) {
        //file save
        for (MultipartFile file : files) {

            if (file != null && !file.isEmpty()) {

                String originalFileName = file.getOriginalFilename();

                Path path = Paths.get(saveFileUrl + File.separator + originalFileName);

                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

                    fileService.insertFile(boardNum, originalFileName, saveFileUrl + File.separator + originalFileName);

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }

        return ResponseEntity.ok().build();
    }

    /**
     * 파일 다운로드
     *
     * @param boardNum
     * @param fileNum
     * @param response
     * @throws IOException
     */
    @GetMapping("/downloadFile")
    public void fileDownload(@RequestParam("boardNum") int boardNum,
                             @RequestParam("fileNum") int fileNum,
                             HttpServletResponse response) throws IOException {

        File file = fileService.downloadFile(boardNum, fileNum);

        String fileName = file.getName();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

        Files.copy(file.toPath(), response.getOutputStream());
    }

    /**
     * 파일 선택 삭제
     *
     * @param boardNum
     * @param fileNum
     * @return
     */

    @GetMapping("/deleteFile")
    public ResponseEntity<?> deleteSelectFile(@RequestParam("boardNum") int boardNum,
                                              @RequestParam("fileNum") int fileNum) {

        fileService.deleteSelectFile(boardNum, fileNum);

        return ResponseEntity.ok().build();
    }
}
