<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>专业查询</title>
<script type="text/javascript">
    	function del_confirm() {
			if(confirm("确定要删除吗？")) {
				return true;
			}else{
				return false;
			}
        }
    </script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/styles.css">
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
					<thead>
						<tr>
							<td>专业名称</td>
							<td>操作</td>
						</tr>
					</thead>

					<c:forEach items="${majors }" var="major">

						<tr class="result">
							<td>${major.name }</td>

							<td>
								<a href="${pageContext.request.contextPath }/MajorServlet?m=toUpdate&id=${major.id}">修改</a>
								| 
								<a href="${pageContext.request.contextPath }/MajorServlet?m=delete&majorId=${major.id}" onclick="return del_confirm();">删除</a></td>
						</tr>
					</c:forEach>
					<tr class="cols2">
						<td colspan="2">
							<a class="btn btn-outline-info" href="${pageContext.request.contextPath }/majoradd.jsp">添加专业</a></td>
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