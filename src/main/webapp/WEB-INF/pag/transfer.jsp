<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<title></title>

</head>
<body>
<h1>ATM系统-交易</h1>

<form action="/card/transfer.do" method="post">
	卡号:${number}
	<input type="hidden" name="number" value="${number}"> <br>
	密码：<input type="password" name="password">	<br>
	汇款账户；<input type="text" name="InNumber"> <br>
	金额；<input type="text" name="money"> <br>
	<input type="submit" value="汇款">
</form>

</body>
</html>