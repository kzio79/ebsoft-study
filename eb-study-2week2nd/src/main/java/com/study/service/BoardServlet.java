package com.study.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * service 클래스에서 구현할 메서드
 */
public interface BoardServlet {
    void excute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
