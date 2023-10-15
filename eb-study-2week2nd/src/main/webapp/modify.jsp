<%@ page import="com.study.model.BoardDTO" %>
<%@ page import="com.study.model.FileDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<html>
<head>
    <title>JSP - 게시판</title>
</head>
<body>
<h1><%= "게시판 - 수정" %>
</h1>
<br/>
<%
    int boardNum = Integer.parseInt(request.getParameter("boardNum"));
    BoardDTO boardDTO = (BoardDTO) request.getAttribute("boardDTO");

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

    if(request.getAttribute("message") != null) {
%>
<script>
    alert('<%=request.getAttribute("message")%>');
    location.href='content?boardNum=<%=boardNum%>'
</script>
<% } %>
    <div style="margin-top: 1%; align-content: center; align-items: center; text-align: center">
        <form action="${pageContext.request.contextPath}/modify" style="margin-bottom: 5%; margin-top: 2%" method="post" enctype="multipart/form-data">
            <table style= "width:80%;">
                <tr>
                    <td style="color: black; font-weight: bolder;">
                        카테고리&nbsp;<%=categoryName%>
                    </td>
                </tr>
                <tr>
                    <td style="color: black; font-weight: bolder;">
                        등록일시&nbsp;<%=boardDTO.getWriterDate()%>
                    </td>
                </tr>
                <tr>
                    <td style="color: black; font-weight: bolder;">
                        수정일시&nbsp;<%=boardDTO.getModifyDate()%>
                    </td>
                </tr>
                <tr>
                    <td style="color: black; font-weight: bolder;">
                        조회수&nbsp;&nbsp;&nbsp;&nbsp;<%=boardDTO.getHit()%>
                    </td>
                </tr>
                <tr>
                    <td style="color: black; font-weight: bolder;">
                        작성자&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="writer" value="<%=boardDTO.getWriter()%>">
                    </td>
                </tr>
                <tr>
                    <td style="color: black; font-weight: bolder;">
                        비밀번호&nbsp;<input type="password" name="pw" value="<%=boardDTO.getPw()%>">
                    </td>
                </tr>
                <tr>
                    <td style="color: black; font-weight: bolder;">
                        글제목&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="title" value=" <%=boardDTO.getTitle()%>">
                    </td>
                </tr>
                <tr>
                    <td style="color: black; font-weight: bolder;">
                        글내용<textarea rows="12" style="width:100%;" class="click" name="content" ><%=boardDTO.getContent()%></textarea>
                    </td>
                </tr>

                <tr>
                    <td style="color: black; font-weight: bolder;">
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
                        <p>
                        <input type="file" id="file" name="file"><p>
                        <input type="file" id="file1" name="file1"><p>
                        <input type="file" id="file2" name="file2"><p>
                    </td>
                </tr>

                <tr style="margin-bottom: 5%">
                    <!-- 글 수정 메뉴 -->
                    <td colspan="2" align="center" style="margin-bottom: 5%">
                        <input type="button" class="btn btn-primary" value="취소" onclick="history.back()">
                        <input type="hidden" name="boardNum" value="<%=boardNum%>">
                        <input type="submit" class="btn btn-primary" value="저장">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>

