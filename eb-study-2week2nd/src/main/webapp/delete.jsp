<%@ page import="com.study.model.BoardDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/delete" />

<%
    int boardNum = Integer.parseInt(request.getParameter("boardNum"));

    BoardDTO boardDTO = (BoardDTO) request.getAttribute("boardDTO");
%>

<html>
<head>
    <title>JSP - 게시판</title>
</head>
<body>
<div style="display: flex; justify-content: center; align-items: center; margin: 20%">
    <article style="text-align: center; border: 3px solid #dddddd; width: 40%">
        <form action="${pageContext.request.contextPath}/delete" name="searchPw" method="post" onsubmit="return checkPw()">
            <table >
                <h3 >비밀번호를 입력하세요</h3><br>
                <input type="password" name="pw" id="pw" placeholder="비밀번호를 입력하세요" required="required">
                <input type="hidden" name="boardNum" id="boardNum" value="<%=boardNum%>">
                <input type="submit" name="pwBtn" value="삭제">
            </table>
        </form>
    </article>
</div>
</body>
</html>

<%--비밀번호 유효성 검사--%>
<script>
    function checkPw(){
        var inputPw = document.getElementById("pw").value;
        var storePw = "<%=boardDTO.getPw()%>";

        if(inputPw === storePw){
            alert("게시물이 삭제되었습니다.")
            window.location.href="/index";
            return true;
        } else {
            alert("비밀번호가 일치하지 않습니다.")
            return false;
        }
    }
</script>
