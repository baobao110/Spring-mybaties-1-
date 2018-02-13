<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<title></title>

</head>
<body>
<h1>ATM系统-销户</h1>

<form action="/card/delete.do" method="post">	
	卡号；${number}
	<input type="hidden" name="number" value="${number}"> <br>
	密码：<input type="password" name="password">	<br>
	<input type="submit" value="销户">
</form>
<!--  这里的action等同于href需要注意的是以后的url设置都如上面这样"/xxx.do? action=xxx"-->
</body>
</html>