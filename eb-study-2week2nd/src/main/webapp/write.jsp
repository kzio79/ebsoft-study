<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<jsp:include page="/write" />

<html>
    <head>
        <title>JSP - 게시판</title>
    </head>
    <body>
    <h1><%= "게시판 - 등록" %>
    </h1>
    <br/>
        <div>
            <div style="margin-top: 2%; margin-left:5%;" >
                <div align="center"  style="margin-top: 5%;">
                    <form action="${pageContext.request.contextPath}/write" enctype="multipart/form-data" style="margin-bottom: 5%; margin-top: 2%" method="post" onsubmit="return checkForm()"  >
                        <table style= "width:80%;">
                            <tr>
                                <td style="color: black; font-weight: bolder;">
                                    카테고리&nbsp;<select id="category" name="category" required>
                                    <option value="">카테고리 선택</option>
                                    <option value="1">JAVA</option>
                                    <option value="2">JS</option>
                                    <option value="3">SpringBoot</option>
                                    <option value="4">Android</option>
                                </select>
                            </tr>
                            <tr>
                                <td style="color: black; font-weight: bolder;">
                                    작성자&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="writer" id="writer" required></td>
                            </tr>
                            <tr>
                                <td style="color: black; font-weight: bolder;">
                                    비밀번호&nbsp;<input type="password" name="pw" id="pw" placeholder="비밀번호" required>
                                    <input type="password" name="pwCheck" id="pwCheck" placeholder="비밀번호확인" required></td>
                            </tr>
                            <tr>
                                <td style="color: black; font-weight: bolder;">
                                    글제목&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="title" id="title" style="width: 90%;" class="click" required></td>
                            </tr>
                            <tr>
                                <td style="color: black; font-weight: bolder;">
                                    글내용<textarea rows="12" style="width:100%;" class="click" name="content" id="content" required></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td style="color: black; font-weight: bolder;">
                                    파일첨부&nbsp;&nbsp;&nbsp;&nbsp;<p>
                                    <input type="file" id="file" name="file" ><p>
                                    <input type="file" id="file1" name="file1" ><p>
                                    <input type="file" id="file2" name="file2" ><p>


                                </td>
                            </tr>
                            <tr style="margin-bottom: 5%">
                                <!-- 글 수정 메뉴 -->
                                <td colspan="2" align="center" style="margin-bottom: 5%">
                                    <input type="button" class="btn btn-primary" value="취소" onclick="location.href='index'">
                                    <input type="submit" class="btn btn-primary" value="저장" >
                                </td>
                            </tr>
                       </table>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>

<script>
    function checkForm() {
        var pw = document.getElementById("pw").value;
        var pwCheck = document.getElementById("pwCheck").value;

        if(pw !== pwCheck){
            alert("비밀번호를 확인해주세요");
            return false;
        }
        return true;
    }
</script>

<%
    if(request.getAttribute("message") != null) { %>
<script>
    alert('<%=request.getAttribute("message")%>');
    location.href='index'
</script>

<%
    }
%>