<%@page import="java.util.List"%>
<%@page import="com.mysql.fabric.xmlrpc.base.Value"%>
<%@page import="ph.entity.Speciality"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" 
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>更改客户信息</title>
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
    	<form action="${pageContext.request.contextPath }/CustomerServlet?m=update" method="post"> 
            <table>
                <tr>
                    <td>客户姓名</td>
                    <td>
                    	<input type="text" name="name" value="${customer.name }" disabled="disabled"/>
                    	<input type="hidden" name="id" value="${customer.id }"/>
                    </td>
                </tr>
                <tr>
                    <td>联系电话</td>
                    <td><input type="text" name="tel" value="${customer.tel }"/></td>
                </tr>
                <tr>
                    <td>家庭地址</td>
                    <td><input type="text" name="address" value="${customer.address }"/></td>
                </tr>
                
                <tr class="cols2">
                    <td colspan="2">
                    <input type="submit" value="更新"/>
                    <input type="reset" value="重置" /></td>
                </tr>
                <tr class="cols2">
					<td colspan="2">
						<input type="button" value="返回" onclick="history.back(-1);" />
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