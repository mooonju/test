<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script type="text/javascript">
    function login_click() {
      let userId = $("#userId").val();
      let password = $("#password").val();

      console.log(userId)
      console.log(password)

      $.ajax({
        type: "post",
        url: "/api/v1/users/login",
        contentType: 'application/json',
        data: JSON.stringify({
          userId: userId,
          password: password
        }),
        success: function (data) {
          location.replace("/view/users")
        },
        error: function (){
          alert("로그인 실패");
        }
      });
    }
  </script>
</head>
<body>
<h3> 로그인 </h3>
<div>

  아이디:<input type="text" id="userId"> <br>
  비밀번호: <input type="password" id="password">

  <button type="button" onclick="login_click()">로그인</button>

</div>
</body>
</html>
