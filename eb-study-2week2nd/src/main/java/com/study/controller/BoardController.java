package com.study.controller;

import com.study.service.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "boardController", urlPatterns = {"/", "/index", "/content", "/write", "/modify", "/delete"})
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
        maxFileSize=1024*1024*10,      // 10MB
        maxRequestSize=1024*1024*50)   // 50MB
public class BoardController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    Map<String, BoardServlet> servletMap = new HashMap<>();

    @Override
    public void init(){

        servletMap.put("/", new BoardListService());
        servletMap.put("/index", new BoardListService());
        servletMap.put("/content", new BoardContentService());
        servletMap.put("/write", new BoardWriteService());
        servletMap.put("/modify", new BoardModifyService());
        servletMap.put("/delete", new BoardDeleteService());

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doAction(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doAction(req, resp);
    }

    /*
    todo : servlet dispatch를 service에서 동작

     */

    protected void doAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        String path = req.getContextPath();
        String command = uri.substring(path.length());

        BoardServlet servlet = this.servletMap.get(command);

        if(command.equals("/")) command="/index";

        if(servlet != null){
            servlet.excute(req,resp);

            if(!resp.isCommitted()){
                RequestDispatcher dp;

                if(command.equals("/index")){ //equals에서 문자열이 앞으로 올수 있게끔
                    dp = req.getRequestDispatcher("index.jsp");
                } else if(command.equals("/write")){
                    dp = req.getRequestDispatcher("write.jsp");
                } else if(command.equals("/content")){
                    dp = req.getRequestDispatcher("content.jsp");
                } else if(command.equals("/modify")){
                    dp = req.getRequestDispatcher("modify.jsp");
                } else if(command.equals("/delete")){
                    dp = req.getRequestDispatcher("delete.jsp");
                }else {
                    dp = req.getRequestDispatcher(command.substring(1)+".jsp");
                }

                dp.forward(req, resp);
            }
        }
    }
}
