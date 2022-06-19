<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@page import="ph.entity.User" %>
<%@page import="java.util.List" %>
	
<%-- taglib指令：引入JSTL标签库的核心库，以便使用JSTL标签语法替代Java脚本 --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>客户查询</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/styles.css">
	<script type="text/javascript">
    	function del_confirm() {
			if(confirm("确定要删除吗？")) {
				return true;
			}else{
				return false;
			}
        }
    </script>
</head>
<body>
<div id="container">
	<div id="header">
        <a id="quit" href="${pageContext.request.contextPath }/QuitServlet">退出</a>
		<h1>社区宠物诊所</h1>
		<ul id="menu">
            <li><a href="${pageContext.request.contextPath }/vetsearch.jsp">医生管理</a></li>
            <li><a href="${pageContext.request.contextPath }/customersearch.jsp">客户管理</a></li>
            <li><a href="${pageContext.request.contextPath }/specsearch.jsp">专业管理</a></li> 
		</ul>
	</div>
	<div id="content">
		<table>
			<thead>
				<tr>
				<td>客户</td>
				<td>操作</td>
			</tr>
			</thead>
			
			<!-- 用JSTL标签语法代替嵌入Java脚本，页面代码风格更统一 -->
			<c:forEach items="${customers }" var="one">
			
			<tr class="result">
				<td>${one.name },${one.tel }</td>
				
				<td>
					<a href="${pageContext.request.contextPath }/CustomerServlet?m=showDetail&cid=${one.id}">查看</a>
					|
					<a href="${pageContext.request.contextPath }/CustomerServlet?m=toUpdate&cid=${one.id}">更新</a>
					|
					<a href="${pageContext.request.contextPath }/CustomerServlet?m=delete&cid=${one.id}" onclick="return del_confirm();">删除</a>
				</td>

					

			</tr>
			</c:forEach>

			<tr class="cols2">
				<td colspan="2">
					<input type="button" value="返回" onclick="history.back(-1);" />
				</td>
			</tr>
			
			<tr class="cols2">
				<td colspan="2" class="info">
				<%=request.getAttribute("msg")==null?"":request.getAttribute("msg") %></td>
			</tr>
		</table>
	</div>
	<div id="footer"></div>
</div>
</body>
</html>
