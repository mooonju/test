<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        function join_click() {
            let userId = $("#userId").val();
            let password = $("#password").val();

            console.log("id 확인: " + userId)
            console.log("pw 확인: " + password)

            $.ajax({
                type: "post",
                url: "/api/v1/users/join",
                contentType: 'application/json',
                data: JSON.stringify({
                    userId: userId,
                    password: password
                }),
                success: function (data) {
                    location.replace("/view/users/login")
                },
                error: function (){
                    alert("에러");
                }
            });
        }
    </script>
</head>
<body>
    <h3> 회원 가입 </h3>
    <div>

        아이디:<input type="text" id="userId"> <br>
        비밀번호: <input type="password" id="password">

        <button type="button" onclick="join_click()">가입</button>

    </div>
</body>
</html>
