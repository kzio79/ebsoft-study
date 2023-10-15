package com.study.service;

import com.study.model.BoardDTO;
import com.study.model.FileDTO;
import com.study.model.ReplyDTO;
import com.study.util.BoardDAO;
import com.study.util.FileDAO;
import com.study.util.ReplyDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BoardContentService implements BoardServlet{

    @Override
    public void excute(HttpServletRequest request, HttpServletResponse response) {

        try {
            int boardNum = Integer.parseInt(request.getParameter("boardNum"));
            String reply = request.getParameter("reply");

            //content 상세보기
            BoardDAO boardDAO = BoardDAO.getInstance();
            BoardDTO boardDTO = boardDAO.viewBoardContent(boardNum);
            boardDAO.updateBoardHit(boardNum);

            //reply 상세보기
            ReplyDAO replyDAO = ReplyDAO.getInstance();
            List<ReplyDTO> replyList= replyDAO.getReplyList(boardNum);
            if(reply != null && !reply.trim().isEmpty()){
                replyDAO.updateReply(boardNum,reply);
            }

            //file 상세보기
            FileDAO fileDAO = FileDAO.getInstance();
            List<FileDTO> fileList = fileDAO.getFileList(boardNum);

            request.setAttribute("boardDTO", boardDTO);
            request.setAttribute("replyDTO", replyList);
            request.setAttribute("fileDTO", fileList);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
