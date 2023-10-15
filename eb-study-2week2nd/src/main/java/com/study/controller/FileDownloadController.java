package com.study.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@WebServlet("/fileDownload")
public class FileDownloadController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String filePath = req.getParameter("filePath");
        File file = new File(filePath);
        String fileName = file.getName();

        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition",
                "attachment;filename="+ URLEncoder.encode(fileName, StandardCharsets.UTF_8));

        Files.copy(file.toPath(), resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
