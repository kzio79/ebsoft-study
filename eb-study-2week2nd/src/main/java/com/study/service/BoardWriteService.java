package com.study.service;


import com.study.util.BoardDAO;
import com.study.util.FileDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
        maxFileSize=1024*1024*10,      // 10MB
        maxRequestSize=1024*1024*50)   // 50MB

public class BoardWriteService implements BoardServlet{

    @Override
    public void excute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html; charset=UTF-8");

        BoardDAO boardDAO = BoardDAO.getInstance();

        String title = request.getParameter("title");
        String writer = request.getParameter("writer");
        String pw = request.getParameter("pw");
        String pwCheck = request.getParameter("pwCheck");
        String content = request.getParameter("content");

        String category = request.getParameter("category");
        int categoryNum = 0;

        if(category != null && !category.isEmpty()){
            try {

                categoryNum = Integer.parseInt(category);

            } catch (NumberFormatException ne){
                ne.printStackTrace();
            }
        }

        if(pw != null && !pw.isEmpty() && pwCheck != null && !pwCheck.isEmpty() && !pw.equals(pwCheck)){

            request.setAttribute("message", "비밀번호를 확인하세요");
            return;
        }

        int result = boardDAO.writeBoardContent(categoryNum, title, writer, pw, content);

        /**
         * file upload
         * todo : file path : 프로퍼티로 설정
         */

        if(result > 0){
            try {
                String path = "D:\\tmp";
                int boardNum = result;

                fileUpload(request.getPart("file"), path, boardNum);
                fileUpload(request.getPart("file1"), path, boardNum);
                fileUpload(request.getPart("file2"), path, boardNum);

                if(result > 0){
                    request.setAttribute("message", "게시물이 등록되었습니다");
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
