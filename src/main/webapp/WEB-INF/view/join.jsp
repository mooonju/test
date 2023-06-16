<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
    <form action="/api/v1/users/join" method="post">

        아이디:<input type="text" name="userId"> <br>
        비밀번호: <input type="password" name="password">

        <input type="submit" value="가입">

    </form>
</body>
</html>
