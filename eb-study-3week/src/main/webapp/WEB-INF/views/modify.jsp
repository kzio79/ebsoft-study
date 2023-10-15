<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>JSP - 게시판</title>
</head>
<body>
<h1><%= "게시판 - 수정" %>
</h1>
<br/>

    <div style="margin-top: 1%; align-content: center; align-items: center; text-align: center">
        <form action="${pageContext.request.contextPath}/modify" style="margin-bottom: 5%; margin-top: 2%" method="post" enctype="multipart/form-data">
            <table style= "width:80%;">
                <tr>
                    <td style="color: black; font-weight: bolder;">
                        카테고리&nbsp;
                    <c:choose>
                        <c:when test="${modifyBoardContent.categoryNum == 1}">JAVA</c:when>
                        <c:when test="${modifyBoardContent.categoryNum == 2}">JS</c:when>
                        <c:when test="${modifyBoardContent.categoryNum == 3}">SpringBoot</c:when>
                        <c:when test="${modifyBoardContent.categoryNum == 4}">Android</c:when>
                    </c:choose>
                    </td>
                </tr>
                <tr>
                    <td style="color: black; font-weight: bolder;">
                        등록일시&nbsp<fmt:formatDate value="${modifyBoardContent.writeDate}" pattern="yyyy-MM-dd" />
                    </td>
                </tr>
                <tr>
                    <td style="color: black; font-weight: bolder;">
                        수정일시&nbsp<fmt:formatDate value="${modifyBoardContent.modifyDate}" pattern="yyyy-MM-dd" />
                    </td>
                </tr>
                <tr>
                    <td style="color: black; font-weight: bolder;">
                        조회수&nbsp;&nbsp;&nbsp;&nbsp;${modifyBoardContent.hit}
                    </td>
                </tr>
                <tr>
                    <td style="color: black; font-weight: bolder;">
                        작성자&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="writer" value="${modifyBoardContent.writer}">
                    </td>
                </tr>
                <tr>
                    <td style="color: black; font-weight: bolder;">
                        비밀번호&nbsp;<input type="password" name="pw" value="${modifyBoardContent.pw}">
                    </td>
                </tr>
                <tr>
                    <td style="color: black; font-weight: bolder;">
                        글제목&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="title" value="${modifyBoardContent.title}">
                    </td>
                </tr>
                <tr>
                    <td style="color: black; font-weight: bolder;">
                        글내용<textarea rows="12" style="width:100%;" class="click" name="content" >${modifyBoardContent.content}</textarea>
                    </td>
                </tr>

                <tr>
                    <td style="color: black; font-weight: bolder;">
                        파일첨부<p>
                        <c:forEach var="viewFileList" items="${viewFileList}">
                        <c:if test="${viewFileList != null}">
                            <a href="${pageContext.request.contextPath}/fileDelete?boardNum=${viewFileList.boardNum}&fileNum=${viewFileList.fileNum}" style="text-decoration:none; color:black">
                                <img src="${pageContext.request.contextPath}../images/delete.jpg" width="1.5%">
                            </a>
                                ${viewFileList.fileName}<br>
                        </c:if>
                        </c:forEach>
                        <input type="file" id="file" name="file"><p>
                        <input type="file" id="file1" name="file1"><p>
                        <input type="file" id="file2" name="file2"><p>
                    </td>
                </tr>

                <tr style="margin-bottom: 5%">
                    <!-- 글 수정 메뉴 -->
                    <td colspan="2" align="center" style="margin-bottom: 5%">
                        <input type="button" class="btn btn-primary" value="취소" onclick="history.back()">
                        <input type="hidden" name="boardNum" value="${modifyBoardContent.boardNum}">
                        <input type="submit" class="btn btn-primary" value="저장">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>

