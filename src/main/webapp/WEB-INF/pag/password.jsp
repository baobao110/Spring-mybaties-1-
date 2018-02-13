<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<title></title>

</head>
<body>
<h1>ATM系统-修改密码</h1>

<form action="/card/password.do" method="post">
	用户；${number}
	<input type="hidden" name="number" value="${number}"> <br>	
	原密码：<input type="password" name="oldPassword">	<br>
	新密码：<input type="password" name="newPassword"><br>
	<input type="submit" value="修改">
</form>
</body>
</html>