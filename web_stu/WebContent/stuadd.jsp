
<%@page import="java.sql.ResultSet"%>
<%@page import="stu.entity.Major"%>
<%@page import="java.util.List"%>
<%@page import="com.mysql.fabric.xmlrpc.base.Value"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加学生</title>
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
			<form action="${pageContext.request.contextPath }/StuServlet?m=add" method="post">
				
				<table>
					<tr>
						<td>学生姓名</td>
						<td><input class="form-control" style="width:380px;" type="text" name="name" /></td>
					</tr>
					<tr>
						<td>学生性别</td>
						<td><input class="form-control" style="width:380px;" type="text" name="gender" /></td>
					</tr>
					<tr>
						<td>学生生日</td>
						<td><input class="form-control" style="width:380px;" type="text" name="birthday" /></td>
					</tr>
					<tr>
						<td>联系方式</td>
						<td><input class="form-control" style="width:380px;" type="text" name="tel" /></td>
					</tr>
					<tr>
						<td>学生专业</td>
						<td>
						<select size="6" name="majorId">
                    	<!-- 使用支持多选的下拉列表显示诊所的所有专长信息，供用户选择，一个医生可以具备多个专业特长 -->	
                    	<option disabled="disabled">请选择所属专业</option>
                    		<%
                    			List<Major> majors = (List<Major>) request.getAttribute("majors");
                    		
                    		//遍历所有专长的集合，每一个专长用一个选项option列出来，value用专长id（这是要写入数据库的），显示用专长name
                    			for (Major m : majors) {
                    		%>
                    			<option value="<%=m.getId()%>"><%=m.getName() %></option>
                    		<%
                    			}
                    		%>	
     
                    	</select>
                    	</td>
					</tr>
					
					
					<tr class="cols2">
						<td colspan="2"><input class="btn btn-info" type="submit" value="添加" /> 
						<input class="btn btn-info" type="reset" value="重置" /></td>
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