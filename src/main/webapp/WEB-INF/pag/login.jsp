<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<title></title>

</head>
<body>
<h1>ATM系统-登录</h1>

<form action="/user/login.do" method="post">	
	用户；<input type="text" name="username"> <br>
	密码：<input type="password" name="password">	<br>
	<input type="submit" value="登录">
</form>

<a href="/user/toRegister.do" target="_blank">注册</a>
</body>
</html>