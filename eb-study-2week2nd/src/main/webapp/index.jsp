<%@ page import="com.study.model.BoardDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/index"/>

<script type='text/javascript'>
    function displayPage(pageNum){
        location.href='index?page='+pageNum;
    }
</script>

<%
    int count = (int) request.getAttribute("count");
    List<BoardDTO> list = (List<BoardDTO>) request.getAttribute("list");

    int currentPage = 1;
    String currentPageStr = request.getParameter("page");
    if(currentPageStr != null && !currentPageStr.isEmpty()){
        currentPage = Integer.parseInt(currentPageStr);
    }

    //페이징 변수계산
    int totalItems = list.size();
    int pageSize =10;

%>

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
                            <option value="">카테고리</option>
                            <option id ="1" value ="1">JAVA</option>
                            <option id ="2" value ="2">JS</option>
                            <option id ="3" value ="3">SpringBoot</option>
                            <option id ="4" value ="4">Android</option></select>

                        <input type ='search' name ='searchId' id ='searchId' size ='35%' placeholder ='검색어를 입력해 주세요(제목+작성자+내용)'>
                        <input type= 'submit' name= 'search' id= 'search' value= '검색'><br/>
                    </td>
                </tr>
            </table>
            <p style= "text-align:left;">총 <%=count %>건 </p>
        </form>

        <table style="text-align: center; width: 100%">
            <thead>
                <tr>
                    <th>No.</th>
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

            <tbody>
            <% for(int i=0; i < list.size() ; i++){
                BoardDTO board = list.get(i);
                String categoryName = "";
                switch(board.getCategoryNum()) {
                    case 1: categoryName = "JAVA"; break;
                    case 2: categoryName = "JS"; break;
                    case 3: categoryName = "SpringBoot"; break;
                    case 4: categoryName = "Android"; break;
                }
            %>

            <tr>
                <td><%=board.getBoardNum()%></td>
                <td><%=categoryName%></td>
                <td></td>
                <td><a href='${pageContext.request.contextPath}/content?boardNum=<%=board.getBoardNum()%>' style="text-decoration:none; color:black"><%=board.getTitle()%></a></td>
                <th><%=board.getWriter()%></th>
                <td><%=board.getHit()%></td>
                <td><%=new SimpleDateFormat("yyyy-MM-dd").format(board.getWriterDate())%></td>
                <td><%=board.getModifyDate().equals(board.getWriterDate())? "-" : new SimpleDateFormat("yyyy-MM-dd").format(board.getModifyDate())%></td>
            </tr>

            <% } %>

            </tbody>
        </table>

        <div style='text-align:center; margin-top:1%; width:100%'>

            <% if(currentPage > 1){ %>
            <button onclick="displayPage(<%=currentPage -1 %>)">이전</button>
            <% } %>

            <%
                int totalPage = (int) Math.ceil((double) count / pageSize);
                for(int i=0; i < totalPage ; i++){
                    if((i+1) == currentPage){
                        out.print(i+1);
                    } else {
                        out.print("<button onclick=\"displayPage("+(i+1)+")\">"+(i+1)+"</button>"); // 각 페이지 번호 출력 (링크 포함)
                    }
                }
            %>

            <% if(currentPage < totalPage){ %>
            <button onclick="displayPage(<%=currentPage +1 %>)">다음</button>
            <% } %>

        </div>

        <table style='text-align:right;width:80%' >
            <tr>
                <td colspan ='5'>
                    <div>
                        <input type="button" value="글쓰기" onclick="location.href='${pageContext.request.contextPath}/write'">
                    </div>
                </td>
            </tr>
        </table>
    </body>
</html>
