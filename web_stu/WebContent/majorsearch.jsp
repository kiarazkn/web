<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>专业查询</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/styles.css">
	<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
  <script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div id="container">
		<div id="header">
			<a id="quit" href="${pageContext.request.contextPath }/QuitServlet">退出</a>
			<h1>学生管理系统</h1>
			<!-- 导航链接-->
			<ul id="menu">
				<li><a href="${pageContext.request.contextPath }/majorsearch.jsp">专业管理</a></li>
				<li><a href="${pageContext.request.contextPath }/stusearch.jsp">学生管理</a></li>
			</ul>
		</div>
		<div id="content">
			<form action="${pageContext.request.contextPath }/MajorServlet?m=search" method="post">
				<table>
					<tr>
						<td>专业名称</td>
						<td><input class="form-control" style="width:380px;" type="text" name="majorName" /></td>
					</tr>

					<tr class="cols2">
						<td colspan="2"><input class="btn btn-info" type="submit" value="查询" /> 
						<input class="btn btn-info" type="reset" value="重置" /></td>
					</tr>
					<tr class="cols2">
						<td colspan="2"><a class="btn btn-outline-info" href="${pageContext.request.contextPath }/majoradd.jsp">添加专业</a></td>
					</tr>

					<tr class="cols2">
						<td colspan="2" class="info"><%=request.getAttribute("msg") == null ? "" : request.getAttribute("msg")%></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="footer"></div>
	</div>
</body>
</html>