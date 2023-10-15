package com.study.service;

import com.study.model.BoardDTO;
import com.study.util.BoardDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.sql.Timestamp;
import java.util.List;

public class BoardListService implements BoardServlet {

    @Override
    public void excute(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html; charset=UTF-8");

        String searchId = request.getParameter("searchId");
        String category = request.getParameter("category");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        Timestamp startDate = null;
        Timestamp endDate = null;

        String pageNumStr = request.getParameter("page");
        String pageSizeStr = request.getParameter("pageSize");

        int categoryNum = 0;
        if(category != null && !category.isEmpty()){
            try {
                categoryNum = Integer.parseInt(category);
            } catch (NumberFormatException ne){
                ne.printStackTrace();
            }
        }

        BoardDAO boardDAO = BoardDAO.getInstance();

        //boardList
        List<BoardDTO> boardList;

        //총건수
        int count = boardDAO.totalBoardCount(searchId,categoryNum,startDate,endDate);
        request.setAttribute("count", count);

        //paging
        int pageNum = 1;
        int pageSize = 10;

        if(pageNumStr != null){
            pageNum = Integer.parseInt(pageNumStr);
        }
        if(pageSizeStr != null){
            pageSize = Integer.parseInt(pageSizeStr);
        }

        //날짜변형
        if(startDateStr != null && !startDateStr.isEmpty()){
            startDate = Timestamp.valueOf(startDateStr + " 00:00:00");
        }

        if(endDateStr != null && !endDateStr.isEmpty()){
            endDate = Timestamp.valueOf(endDateStr + " 23:59:59");
        }

        boolean selectItem = searchId != null || category != null;

        if(selectItem){

            try {
                //검색후 BoardList로 등록
                boardList = boardDAO.selectContent(searchId, categoryNum, startDate, endDate, pageNum, pageSize);
                request.setAttribute("list", boardList);

                if(boardList != null){

                }
            } catch (NumberFormatException ne){
                ne.printStackTrace();
            }
        }else{
            //검색없이 BoardList로 등록
            boardList = boardDAO.getBoardList(pageNum,pageSize);

            if(boardList != null){
                request.setAttribute("list", boardList);
            }
        }
    }
}
