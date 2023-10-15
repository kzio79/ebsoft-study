<%@ page import="com.study.model.BoardDTO" %>
<%@ page import="com.study.model.ReplyDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.study.util.ReplyDAO" %>
<%@ page import="com.study.model.FileDTO" %>
<%@ page import="com.study.util.FileDAO" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/content" />

<%

    int boardNum = Integer.parseInt(request.getParameter("boardNum"));
    BoardDTO boardDTO = (BoardDTO) request.getAttribute("boardDTO");

    if(boardDTO != null){
        String categoryName = "";
        switch(boardDTO.getCategoryNum()) {
            case 1:
                categoryName = "JAVA";
                break;
            case 2:
                categoryName = "JS";
                break;
            case 3:
                categoryName = "SpringBoot";
                break;
            case 4:
                categoryName = "Android";
                break;
            default:
                categoryName = "";
        }
%>

<html>
<head>
    <title>JSP - 게시판</title>
</head>
<body>
<h1><%="게시판 - 보기"%>
</h1>
<br/>

<div align="center" style="margin-top: 2%; width: 80%">

    <div style="display:flex; justify-content: space-between; margin-left: 10%">
        <td name="writer" style="color: black; font-weight: bolder; " aria-readonly="true">
            <%=boardDTO.getWriter()%>
        </td>

        <div>
            <td name="writedate" style="align-items:end; color: black; font-weight: bolder;" aria-readonly="true">
                등록일시&nbsp<%=boardDTO.getWriterDate()%>
            </td>
            &nbsp&nbsp&nbsp&nbsp
            <td name="modifydate" style="color: black; font-weight: bolder;" aria-readonly="true">
                수정일시&nbsp<%=boardDTO.getModifyDate()%>

            </td>
        </div>
    </div>

    <div style="display:flex; justify-content: space-between; margin-left: 10%">
        <td name="category" style="color: black; font-weight: bolder;" aria-readonly="true">
            [<%=categoryName%>]&nbsp<%=boardDTO.getTitle()%>
        </td>
        <div>
            <td name="hit" style="color: black; font-weight: bolder;" aria-readonly="true">
                조회수&nbsp<%=boardDTO.getHit()%>
            </td>
        </div>
    </div>
</div>

<%
    }
%>


<hr>
<div >
    <div align="center"   style="margin-top: 1%;">
        <form action="${pageContext.request.contextPath}/delete" style="margin-bottom: 5%; margin-top: 2%" method="post">
            <table style= "width:80%;">
                <tr >
                    <td style="color: black; font-weight: bolder;">
                        <textarea rows="12" style="text-align: left; width: 80%;" class="click" name="content" id="content" readonly>
                        <%=boardDTO.getContent()%>
                        </textarea>
                    </td>
                </tr>
                <tr>
                    <td style="color: black; font-weight: bolder;">
                    <ul>
                        <br>등록된 파일<p>
                        <%
                            List<FileDTO> fileList = (List<FileDTO>) request.getAttribute("fileDTO");
                            if(fileList != null){
                                for(FileDTO listFile : fileList){
                        %>
                        <a href="${pageContext.request.contextPath}/fileDownload?filePath=<%=java.net.URLEncoder.encode(listFile.getFilePath(),StandardCharsets.UTF_8)%>" style="text-decoration:none; color:black">
                            <%=listFile.getFileName()%><br></a>
                        <%
                            }}
                        %>
                    </ul>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center" style="margin-bottom: 5%">
                        <input type="button" class="btn btn-primary" value="목록" onclick="location.href='index'">
                        <input type="button" class="btn btn-primary" value="수정" onclick="location.href='modify?boardNum=<%=boardNum%>'">
                        <input type="hidden" name="boardNum" value="<%=boardNum%>">
                        <input type="submit" class="btn btn-primary" value="삭제">
                    </td>
                </tr>
            </table>
        </form>

<%--        댓글부분--%>
        <div>
            <div align="center" style="margin: 2%;">
                <form id="replyFresh" action="${pageContext.request.contextPath}/replyUpdate" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="boardNum" value="<%=boardNum%>">
                    <table style="border: 1px solid #dddddd; width: 80%">
                        <tr>
                            <td style="border-bottom:none;" valign="middle"></td>
                            <td style="color: black; font-weight: bolder;" id="replyList">

                                <%

                                    ReplyDAO replyDAO = ReplyDAO.getInstance();

                                    List<ReplyDTO> replyList = replyDAO.getReplyList(boardNum);
                                    for(ReplyDTO list : replyList){

                                %>
                                <%=list.getReplyDate()%><br>
                                <%=list.getContent()%><hr>
                                <%
                                        }
                                %>

                            </td>
                        </tr>
                        <tr>
                            <td style="border-bottom:none;" valign="middle"></td>
                            <td style="color: black; font-weight: bolder;">
                                <textarea rows="2" style="width:100%;" name="reply" id="reply" placeholder="댓글을 입력해 주세요"></textarea>
                            </td>
                            <td>
                            <input type="hidden" name="boardNum" id="boardNum" value="<%=boardNum%>">
                            <input type="submit" class="btn btn-primary pull" value="등록">
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
</div>
</body>
</html>
