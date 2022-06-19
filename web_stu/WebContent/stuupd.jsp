<%@page import="java.util.List"%>
<%@page import="com.mysql.fabric.xmlrpc.base.Value"%>
<%@page import="stu.entity.Major"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" 
	pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>更改学生信息</title>
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
    	<form action="${pageContext.request.contextPath }/StuServlet?m=update" method="post"> 
            <table>
                <tr>
                    <td>学生姓名</td>
                    <td>
                    	<input class="form-control" style="width:380px;" type="text" name="name" value="${stu.name }"/>
                    	<input type="hidden" name="id" value="${stu.id }"/>
                    </td>
                </tr>
                <tr>
                    <td>学生性别</td>
                    <td>
                    	<input class="form-control" style="width:380px;" type="text" name="gender" value="${stu.gender }"/>
                    </td>
                </tr>
                <tr>
                    <td>学生生日</td>
                    <td>
                    	<input class="form-control" style="width:380px;" type="text" name="birthday" value="${stu.birthday }"/>
                    </td>
                </tr>
                  <tr>
                    <td>学生电话</td>
                    <td>
                    	<input class="form-control" style="width:380px;" type="text" name="tel" value="${stu.tel }"/>
                    </td>
                </tr>
                
             <%--     <tr>
                    <td>专业特长</td>
                     <td>
                    	<select size="6" name="majorId">
                    		<option disabled="disabled">请选择至少一项</option>
                    		
                    		<%
                    		List<Major> allMajors = (List<Major>)request.getAttribute("allMajors");
                    		//所有专长
                    		
                    		List<Major> majors = (List<Major>)request.getAttribute("majors");
                    		//医生原具备特长
                    		outloop : for(Major major : allMajors){//外层循环，遍历所有专长
                    			
                    			for(Major selected : majors){//内层循环，遍历医生原本具备的专长
                    				
                    				if(major.getId()==selected.getId()){
                    					
                    					//若某专长是医生原本具备的专长，则令其为选中状态
                    					out.println("<option value='"+major.getId()+"' selected='selected'>"+major.getName()+"</option>");
                    					
                    					continue outloop;//结束本轮循环，继续新一轮外层循环
                    				}
                    			}
                    		//某专长不是医生原本具备的专长，则不是选中状态
                    		out.println("<option value='"+major.getId()+"'>"+major.getName()+"</option>");
                    		}
                    		%>
                    	</select>
                    </td>
                </tr> --%>
                
                <tr class="cols2">
                    <td colspan="2">
                    <input class="btn btn-info" type="submit" value="修改"/>
                    <input class="btn btn-info" type="reset" value="重置" /></td>
                </tr>
                <tr class="cols2">
					<td colspan="2">
						<input class="btn btn-outline-info" type="button" value="返回" onclick="history.back(-1);" />
					</td>
				</tr>
                <tr class="cols2">
                    <td colspan="2" class="info">
                    <%=request.getAttribute("msg")==null?"":request.getAttribute("msg") %></td>
                </tr>
            </table>
        </form>
    </div>
    <div id="footer"></div>
</div>
</body>
</html>