<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>

<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<title>ATM系统--用户中心</title>

</head>

<body>
<h1>个人中心页面</h1>
<img src="/user/showPicture.do" width="66px" height="66px" onerror="javascript:this.src='/images/dayuan.jpg';" >
<form method="POST" enctype="multipart/form-data" action="/user/load.do">
<input type="file" name="upfile"><br/>
 <input type="submit" value="上传">
 </form>
<h1>用户:${username}</h1>
<a href="/card/toOpenAccount.do" target="_blank">开户</a><br>
<a href="/user/login.do" target="_blank">退出</a><br>

</body>

<table>
	<tr>
		<td>卡号</td>
		<td>金额</td>
		<td>时间</td>
		<td>手续</td>
	</tr>
	
	<c:forEach items="${fenye.getObject()}" var="list">
		<tr>
			<td>${list.getNumber()}</td>
			<td>${list.getMoney()}</td>
			<td><fmt:formatDate value="${list.getCreatetime()}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>
			<a href="/card/toSave.do?number=${list.getNumber()}" target="_blank">存钱</a>	<!--这边需要特别注意链接参数  -->
			<a href="/card/toCheck.do?number=${list.getNumber()}" target="_blank">取钱</a>
			<a href="/card/toTransfer.do?number=${list.getNumber()}" target="_blank">汇款</a>
			<a href="/card/toList.do?number=${list.getNumber()}" target="_blank">交易</a>
			<a href="/card/toChangePassword.do?number=${list.getNumber()}" target="_blank">修改密码</a>
			<a href="/card/toDelete.do?number=${list.getNumber()}" target="_blank">销户</a>
			</td>
		</tr>
	</c:forEach>
	
	<c:if test="${not empty fenye }">
		<button onclick="first()">首页</button>
		<button onclick="pre()">上一页</button>
		<button onclick="next()">下一页</button>
		<button onclick="last()">尾页</button>
		${fenye.getCurrentPage()}/${fenye.getTotalPage()}
	</c:if>
	
</table>

</body>

<script type="text/javascript">

	var currentPage = "${fenye.getCurrentPage()}";
	
	var totalPageNum = "${fenye.getTotalPage()}";
	/* 这里需要注意分页技术的前端实现 ，注意false返回 */
	
	function next() {
		if (currentPage == totalPageNum) {
			return false;
		}
		
		currentPage = parseInt(currentPage) + 1;
		window.location.href='/user/toUsercenter.do?currentPage=' + currentPage;
	}

	function pre() {
		
		if (currentPage == 1) {
			return false;
		}
		
		currentPage = parseInt(currentPage) - 1;
		window.location.href='/user/toUsercenter.do?currentPage=' + currentPage;
	}

	function first() {
		currentPage = 1;
		window.location.href='/user/toUsercenter.do?currentPage=' + currentPage;
	}

	function last() {
		currentPage = totalPageNum;
		window.location.href='/user/toUsercenter.do?currentPage=' + currentPage;
	}
		
	
</script>


</html>