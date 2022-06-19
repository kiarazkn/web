<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" %>
	
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加宠物病例</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/styles.css">
</head>
<body>
<div id="container">
    <div id="header">
        <a id="quit" href="${pageContext.request.contextPath }/QuitServlet">退出</a>	
        <h1>社区宠物诊所</h1>
        <!-- 导航链接-->
        <ul id="menu">
            <li><a href="${pageContext.request.contextPath }/vetsearch.jsp">医生管理</a></li>
            <li><a href="${pageContext.request.contextPath }/customersearch.jsp">客户管理</a></li>
            <li><a href="${pageContext.request.contextPath }/specsearch.jsp">专业管理</a></li>
        </ul>
    </div>
    
    <div id="content">
    
        <form action="${pageContext.request.contextPath }/VisitServlet?m=add" method="post">
        <!-- 此表单包含上传文件控件，必须设置：method="post" enctype="multipart/form-data" -->    
            <table>
                <tr>
                    <td>宠物姓名</td>
                    <td>
                    	<input type="text" name="petName" disabled="disabled" value="${param.petName}"/>
                    	<input type="hidden" name="petId" value="${param.petId}"/>
                    	<input type="hidden" name="cid" value="${param.cid}"/>
                    	<!-- hidden:用隐藏域传petId和cid -->
                    </td>
                </tr>
                <tr>
                    <td>主治医生</td>
                    <td>
                    <select name="vetId">
                    	<c:forEach items="${vets}" var="v">
                    		<option value="${v.id}">${v.name}</option>
                    	</c:forEach>
                    </select>
                    </td>
                </tr>
                
                <tr>
                	<td>病情描述</td>
                	<td><textarea rows="4" name="description"></textarea></td>
                </tr>
                <tr> 
                	<td>治疗方案</td>
                	<td><textarea rows="4" name="treatment"></textarea></td>
                </tr>
                <tr class="cols2">
                    <td colspan="2">
                    <input type="submit" value="添加"/>
                    <input type="reset" value="重置" /></td>
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