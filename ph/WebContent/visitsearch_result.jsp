<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>病历查询结果</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/styles.css">
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
		<table class="wide">
			<thead>
				<tr>
				<td>医生</td>
				<td>时间</td>
				<td>病情描述</td>
				<td>治疗方案</td>
			</tr>
			</thead>
			
			<c:forEach items="${visits }" var="one">
				<tr class="result">
					<td>${one.vetName }</td>
					<td>${one.visitdate }</td>
					<td>${one.description }</td>
					<td>${one.treatment }</td>
				</tr>
			</c:forEach>
			
			<tr class="cols4">
				<td colspan="4">
					<input type="button" value="返回" onclick="history.back(-1)" />
				</td>
			</tr>
			
			<tr class="cols4">
				<td colspan="4" class="info">
					<%=request.getAttribute("msg")==null?"":request.getAttribute("msg") %>
				</td>
			</tr>
		</table>
	</div>
	<div id="footer"></div>
</div>
</body>
</html>
