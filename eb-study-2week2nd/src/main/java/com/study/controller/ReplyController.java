package com.study.controller;

import com.study.util.ReplyDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/replyUpdate")
public class ReplyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String reply = req.getParameter("reply");
        int boardNum = Integer.parseInt(req.getParameter("boardNum"));

        ReplyDAO replyDAO = ReplyDAO.getInstance();

        replyDAO.updateReply(boardNum,reply);

        resp.sendRedirect(req.getContextPath() + "/content?boardNum=" + boardNum);

    }
}
