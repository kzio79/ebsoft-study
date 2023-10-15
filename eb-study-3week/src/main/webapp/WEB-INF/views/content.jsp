<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>JSP - 게시판</title>
    </head>
    <body>
        <h1><%="게시판 - 보기"%></h1>
    <br/>

    <div align="center" style="margin-top: 2%; width: 80%">

        <div style="display:flex; justify-content: space-between; margin-left: 10%">
            <td name="writer" style="color: black; font-weight: bolder; " aria-readonly="true">
               ${viewBoardContent.writer}
            </td>

            <div>
                <td name="writeDate" style="align-items:end; color: black; font-weight: bolder;" aria-readonly="true">
                    등록일시&nbsp<fmt:formatDate value="${viewBoardContent.writeDate}" pattern="yyyy-MM-dd" />
                </td>
                &nbsp&nbsp&nbsp&nbsp
                <td name="modifyDate" style="color: black; font-weight: bolder;" aria-readonly="true">
                    수정일시&nbsp<fmt:formatDate value="${viewBoardContent.modifyDate}" pattern="yyyy-MM-dd" />

                </td>
            </div>
        </div>

        <div style="display:flex; justify-content: space-between; margin-left: 10%">
            <td name="categoryNum" style="color: black; font-weight: bolder;" aria-readonly="true">
                [<c:choose>
                    <c:when test="${viewBoardContent.categoryNum == 1}">JAVA</c:when>
                    <c:when test="${viewBoardContent.categoryNum == 2}">JS</c:when>
                    <c:when test="${viewBoardContent.categoryNum == 3}">SpringBoot</c:when>
                    <c:when test="${viewBoardContent.categoryNum == 4}">Android</c:when>
                </c:choose>]${viewBoardContent.title}
            </td>
            <div>
                <td name="hit" style="color: black; font-weight: bolder;" aria-readonly="true">
                    조회수&nbsp${viewBoardContent.hit}
                </td>
            </div>
        </div>
    </div>
    <hr>
    <div >
        <div align="center"   style="margin-top: 1%;">
            <form action="${pageContext.request.contextPath}/modify" name="regform" style="margin-bottom: 5%; margin-top: 2%">
                <table style= "width:80%;">
                    <tr >
                        <td style="color: black; font-weight: bolder;">
                            <textarea rows="12" style="text-align: left; width: 80%;" class="click" name="content" id="content" readonly>
                                ${viewBoardContent.content}
                            </textarea>
                        </td>
                    </tr>

<%--fileView, fileDownload--%>
                    <tr>
                        <ul>
                            <td style="color: black; font-weight: bolder;">
                                <br>등록된 파일<p>
                                <c:forEach var="viewFileList" items="${viewFileList}">
                                    <c:if test="${viewFileList != null}">
                                        <a href="${pageContext.request.contextPath}/download?boardNum=${viewFileList.boardNum}&fileNum=${viewFileList.fileNum}" style="text-decoration:none; color:black">
                                                <img src="${pageContext.request.contextPath}../images/file.jpg" width="1.5%">
                                        </a>
                                        ${viewFileList.fileName}<br>
                                    </c:if>
                                </c:forEach>
                            </td>
                        </ul>
                    </tr>

                    <tr>
                        <td colspan="2" align="center" style="margin-bottom: 5%">
                            <input type="button" class="btn btn-primary" value="목록" onclick="location.href='index'">
                            <input type="hidden" name="boardNum" value="${viewBoardContent.boardNum}">
                            <input type="submit" class="btn btn-primary" value="수정" >
                            <input type="button" class="btn btn-primary" value="삭제" onclick="location.href='delete?boardNum=${viewBoardContent.boardNum}'">
                        </td>
                    </tr>
                </table>
            </form>

    <%--        댓글부분--%>
            <div>
                <div align="center" style="margin: 2%;">
                    <form id="reply" action="${pageContext.request.contextPath}/content?boardNum=${viewBoardContent.boardNum}" method="post" onsubmit="return replyNull()">
                        <input type="hidden" name="boardNum" value="${viewBoardContent.boardNum}">
                        <table style="border: 1px solid #dddddd; width: 80%">
                            <c:forEach var="viewReplyContent" items="${viewReplyContent}">
                            <tr>
                                <td style="border-bottom:none;" valign="middle"></td>
                                <td style="color: black; font-weight: bolder;" id="replyList">
                                    ${viewReplyContent.replyDate}<br>
                                    ${viewReplyContent.content}<hr>
                                </td>
                            </tr>
                            </c:forEach>

                            <tr>
                                <td style="border-bottom:none;" valign="middle"></td>
                                <td style="color: black; font-weight: bolder;">
                                    <textarea rows="2" style="width:100%;" name="replyContent" id="replyContent" placeholder="댓글을 입력해 주세요"></textarea>
                                </td>
                                <td>
                                <input type="hidden" name="boardNum" id="boardNum" value="${viewBoardContent.boardNum}">
                                <input type="submit" class="btn btn-primary pull" value="등록">
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>

<script>
    function replyNull(){
        var replyContent = document.getElementById("replyContent").value;

        if(replyContent === ""){
            alert("댓글을 입력하세요");
            return false;
        }
    }
</script>