<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生查询</title>
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
			<form action="${pageContext.request.contextPath }/StuServlet?m=search" method="post"style="width=600px;">

				<table>
					<thead>
						<tr>
							<td>姓名</td>
							<td>性别</td>
							<td>生日</td>
							<td>联系方式</td>
							<td>专业</td>
							<td>操作</td>
						</tr>
					</thead>

					<c:forEach items="${stus }" var="stu">

						<tr class="result">
							<td>${stu.name }</td>
							<td>${stu.gender }</td>
							<td>${stu.birthday }</td>
							<td>${stu.tel }</td>
							<td><c:forEach items="${stu.majors }" var="major">
								${major.name }&nbsp;&nbsp;
							</c:forEach></td>
							<td>
								<a href="${pageContext.request.contextPath }/StuServlet?m=toUpdate&id=${stu.id}">修改</a>
								| 
								<a href="${pageContext.request.contextPath }/StuServlet?m=delete&stuId=${stu.id}" onclick="return del_confirm();">删除</a></td>
						</tr>
					</c:forEach>
					<tr class="cols2">
						<td colspan="2">
							<a class="btn btn-outline-info" href="${pageContext.request.contextPath }/StuServlet?m=toAdd">添加学生</a></td>
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