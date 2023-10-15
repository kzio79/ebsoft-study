<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>JSP - 게시판</title>
    </head>

    <body>
        <h1><%= "자유게시판 - 목록"%></h1><br/>
        <form action="${pageContext.request.contextPath}/index" accept-charset="UTF-8">
            <table style='text-align:center; border: 1px solid #dddddd; width:80%'>
                <tr>
                    <td>등록일 <input type='date' name='startDate'> ~
                        <input type='date' name='endDate' value='<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>' >
                        <select id ='category' name ='category'>
                            <option id="0" value="0">카테고리</option>
                            <option id ="1" value ="1">JAVA</option>
                            <option id ="2" value ="2">JS</option>
                            <option id ="3" value ="3">SpringBoot</option>
                            <option id ="4" value ="4">Android</option></select>

                        <input type ='search' name ='searchId' id ='searchId' size ='35%' placeholder ='검색어를 입력해 주세요(제목+작성자)'>
                        <input type= 'submit' name= 'search' id= 'search' value= '검색'><br/>
                    </td>
                </tr>
            </table>
            <p style= "text-align:left;">총 ${count} 건 </p>
        </form>

        <table style="text-align: center; width: 100%">
            <thead>
                <tr>
                    <th>카테고리</th>
                    <th></th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>조회수</th>
                    <th>등록 일자</th>
                    <th>수정 일자</th>
                    <hr>
                </tr>
            </thead>

<%--list--%>
            <tbody>
                <c:forEach var="boardList" items="${boardList}">
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${boardList.categoryNum == 1}">JAVA</c:when>
                            <c:when test="${boardList.categoryNum == 2}">JS</c:when>
                            <c:when test="${boardList.categoryNum == 3}">SpringBoot</c:when>
                            <c:when test="${boardList.categoryNum == 4}">Android</c:when>
                        </c:choose>
                    </td>
                        <th style="padding: 0; margin: 0;">
                            <c:if test="${boardList.fileCount > 0}">
                                <img src="${pageContext.request.contextPath}../images/file.jpg" width="10%" style="margin: 0;padding: 0;">
                            </c:if>
                        </th>
                    <td><a href="${pageContext.request.contextPath}/content?boardNum=${boardList.boardNum}" style="text-decoration: none; color: black"> ${boardList.title}</a></td>
                    <td>${boardList.writer}</td>
                    <td>${boardList.hit}</td>
                    <td><fmt:formatDate value="${boardList.writeDate}" pattern="yyyy-MM-dd"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${empty boardList.modifyDate || boardList.modifyDate eq boardList.writeDate}">-</c:when>
                            <c:otherwise><fmt:formatDate value="${boardList.modifyDate}" pattern="yyyy-MM-dd"/></c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>

<%--paging--%>
        <div style='text-align:center; margin-top:1%; width:100%'>

            <c:if test="${pageNum > 1}" >
                <button onclick="displayPage(${pageNum -1})">이전</button>
            </c:if>
            <c:forEach var="i" begin="1" end="${totalPage}">
                <button onclick="displayPage(${i})">${i}</button>
            </c:forEach>
            <c:if test="${pageNum < totalPage}">
                <button onclick="displayPage(${pageNum +1})">다음</button>
            </c:if>

        </div>

        <table style='text-align:right;width:80%' >
            <tr>
                <td colspan ='5'>
                    <div class="form-group">
                        <input type= 'button' value ='등록' class ='btn btn-primary' size ='20' onclick="location.href='write'">
                    </div>
                </td>
            </tr>
        </table>
    </body>
</html>

<script type='text/javascript'>
    function displayPage(pageNum){
        location.href='index?pageNum='+pageNum;
    }
</script>