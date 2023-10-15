package com.study.service;

import com.study.model.BoardDTO;
import com.study.util.BoardDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * todo : 비밀번호 유효성 검사
 */
public class BoardDeleteService implements BoardServlet{

    @Override
    public void excute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int boardNum = Integer.parseInt(request.getParameter("boardNum"));
        String pw = request.getParameter("pw");

        BoardDAO boardDAO = BoardDAO.getInstance();
        BoardDTO boardDTO = boardDAO.viewBoardContent(boardNum);

        request.setAttribute("boardDTO",boardDTO);

        if(pw != null && pw.equals(boardDTO.getPw())){
            boardDAO.deleteBoardContent(boardNum);
            response.sendRedirect("index");

        } else {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("비밀전호가 일치하지 않습니다111.");
        }


    }
}
