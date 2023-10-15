package com.study.service;

import com.study.model.BoardDTO;
import com.study.model.FileDTO;
import com.study.util.BoardDAO;
import com.study.util.FileDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
        maxFileSize=1024*1024*10,      // 10MB
        maxRequestSize=1024*1024*50)   // 50MB

public class BoardModifyService implements BoardServlet{

    @Override
    public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        int boardNum = Integer.parseInt(request.getParameter("boardNum"));

        //content 보기
        BoardDAO boardDAO = BoardDAO.getInstance();
        BoardDTO boardDTO = boardDAO.viewBoardContent(boardNum);

        //file 보기
        FileDAO fileDAO = FileDAO.getInstance();
        List<FileDTO> fileList = fileDAO.getFileList(boardNum);

        request.setAttribute("boardDTO", boardDTO);
        request.setAttribute("fileDTO", fileList);

        //content 수정
        String title = request.getParameter("title");
        String writer = request.getParameter("writer");
        String pw = request.getParameter("pw");
        String content = request.getParameter("content");

        int result = boardDAO.modifyBoardContent(writer,pw,title,content,boardNum);

        //file upload
        if(result > 0){
            try {
                String path = "D:\\tmp";

                fileUpload(request.getPart("file"), path, boardNum);
                fileUpload(request.getPart("file1"), path, boardNum);
                fileUpload(request.getPart("file2"), path, boardNum);

                if(result > 0){
                    request.setAttribute("message", "게시물이 수정 되었습니다.");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * fileUpload
     * @param filePart
     * @param path
     * @param boardNum
     * @throws IOException
     */
    private void fileUpload(Part filePart, String path, int boardNum) throws IOException {
        if (filePart != null && filePart.getSize() > 0) {

            String fileName = filePart.getSubmittedFileName();
            File fileSaveDir = new File(path);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }

            filePart.write(path + File.separator + fileName);

            FileDAO fileDAO = FileDAO.getInstance();
            String filePathForDB = path + File.separator + fileName;
            fileDAO.updateFile(boardNum, fileName, filePathForDB);

        }
    }
}
