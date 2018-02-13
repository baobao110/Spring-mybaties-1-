<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<title></title>

</head>
<body>
<h1>ATM系统-取钱</h1>

<form action="/card/check.do" method="post">	
	卡号；${number}
	<input type="hidden" name="number" value="${number}"> <br>
	密码：<input type="password" name="password">	<br>
	金额；<input type="text" name="money"> <br>
	<input type="submit" value="取钱">
</form>
<!--  这里的action等同于href需要注意的是以后的url设置都如上面这样"/xxx.do? action=xxx"-->
</body>
</html>