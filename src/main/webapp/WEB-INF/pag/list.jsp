<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>

<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">

</head>

<body>
<h1>流水页面</h1>
<form id="List" name="form" action="/card/list.do" method="post">
卡号；${number}
<input type="hidden" name="number" value="${number}"> <br>		<!--注意这里的name和value,name是用于参数的名称,value为当前文本的值  -->
密码：<input type="password" name="password" value="${password}">	<br>
<input type="hidden" id="currentPage" name="currentPage" value="1">
<input type="submit" value="查询流水"><br>
</form>
<button onclick="flow()">"打印"</button>

<table>
	<tr>
		<td>卡号</td>
		<td>金额</td>
		<td>备注</td>
		<td>时间</td>
	</tr>
	
	<c:forEach items="${fenye.getObject()}" var="list">
		<tr>
			<td>${list.getNumber()}</td>
			<td>${list.getMoney()}</td>
			<td>${list.getDescription()}</td>
			<td><fmt:formatDate value="${list.getCreatetime()}" pattern="yyyy年MM月dd日       HH时mm分ss秒"/></td>
		</tr>
	</c:forEach>
	
	<c:if test="${not empty fenye }">
		<button onclick="first();">首页</button>
		<button onclick="pre();">上一页</button>
		<button onclick="next();">下一页</button>
		<button onclick="last();">尾页</button>
		${fenye.getCurrentPage()}/${fenye.getTotalPage()}
	</c:if>
	
</table>

</body>

<script type="text/javascript">

	var currentPage = "${fenye.getCurrentPage()}";
	
	var totalPageNum = "${fenye.getTotalPage()}";
	
	function flow(){
		 document.forms['form'].action= "/card/down.do";
		 document.getElementById("List").submit();
	}
	function next() {
		if (currentPage == totalPageNum) {
			return false;
		}
		
		currentPage=parseInt(currentPage) + 1;
		document.getElementById("currentPage").value= currentPage;
		document.getElementById("List").submit();
	}
	
	function pre() {
		
		if (currentPage == 1) {
			return false;
		}
		
		currentPage = parseInt(currentPage) - 1;
		document.getElementById("currentPage").value= currentPage;
		document.getElementById("List").submit();
	}
	
	function first() {
		currentPage = 1;
		document.getElementById("currentPage").value= currentPage;
		document.getElementById("List").submit();
	}
	
	function last() {
		currentPage = totalPageNum;
		document.getElementById("currentPage").value= currentPage;
		document.getElementById("List").submit();
	}
	
	
</script>

<a href="/user/toUsercenter.do">返回个人中心</a>
</html>
