<%@ page language="java" contentType="text/html;charset=UTF-8" 
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>医生管理</title>
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
        <form action="${pageContext.request.contextPath }/VetServlet?m=search" method="post">
        <!-- 通过m参数传递请求的具体操作（增删改查） -->    
            <table>
                <tr>
                    <td>医生姓名</td>
                    <td><input type="text" name="vetName"/></td>
                </tr>
                <tr>
                    <td>专业特长</td>
                    <td><input type="text" name="specName"/></td>
                </tr>
                <tr class="cols2">
                    <td colspan="2">
                    <input type="submit" value="查询"/>
                    <input type="reset" value="重置" /></td>
                </tr>
                <tr class="cols2">
                    <td colspan="2">
                    <a href="${pageContext.request.contextPath }/VetServlet?m=toAdd">添加医生</a></td>
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