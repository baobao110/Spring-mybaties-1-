<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>

<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<title>ATM系统-注册</title>

</head>

<body>
<h1>注册页面</h1>
<form  action="/user/Register.do" method="post">
用户名:<input type="text" name="username">
密码:<input type="password" name="password">
<input type="submit" value="注册">
</form>

</body>


</html>