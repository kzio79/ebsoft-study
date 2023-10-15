<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>JSP - 게시판</title>
</head>
<body>
<div style="display: flex; justify-content: center; align-items: center; margin: 20%">
    <article style="text-align: center; border: 3px solid #dddddd; width: 40%">
        <form action="${pageContext.request.contextPath}/delete?boardNum=${deleteContent.boardNum}" name="searchPw" method="post" onsubmit="return checkPw()">
            <table >
                <h3 >비밀번호를 입력하세요</h3><br>
                <input type="password" name="pw" id="pw" placeholder="비밀번호를 입력하세요" required="required">
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
        var storePw = "${deleteContent.pw}";

        if(inputPw === storePw){
            alert("게시물이 삭제되었습니다.")
            location.href="index";
            return true;
        } else {
            alert("비밀번호가 일치하지 않습니다.")
            return false;
        }
    }
</script>
