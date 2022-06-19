<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加宠物</title>
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
    
        <form action="${pageContext.request.contextPath }/SpecServlet?m=add" method="post">
            <table>
                <tr>
                    <td>专业名称</td>
                    <td><input type="text" name="name" /></td>
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