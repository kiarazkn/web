<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" import="java.util.*,ph.entity.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>医生查询结果</title>
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
		<table>
		<thead>
			<tr>
				<td>医生姓名</td>
				<td>专业特长</td>
				<td>操作</td>
			</tr>
		</thead>
			<c:forEach items="${vets }" var="vet">
				<tr class="result">
					<td>${vet.name }</td>
					<td>
						<c:forEach items="${vet.specs }" var="spec">
							${spec.name }&nbsp;&nbsp;
						</c:forEach>
					</td>
					<td>
						<a href="${pageContext.request.contextPath }/VetServlet?m=toUpdate&vetId=${vet.id}&vetName=${vet.name}">修改</a>
					</td>
				</tr>
			</c:forEach>
			
			<tr class="cols2">
				<td colspan="2"><input type="button" value="返回" onclick="history.back(-1);" /></td>
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
