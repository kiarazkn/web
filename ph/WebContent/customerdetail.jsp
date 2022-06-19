<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@page import="ph.entity.User" %>
<%@page import="java.util.List" %>
	
<%-- taglib指令：引入JSTL标签库的核心库，以便使用JSTL标签语法替代Java脚本 --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>客户详情信息</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/styles.css">
    <script type="text/javascript">
    	function del_confirm() {
			if(confirm("确定要删除吗？")) {
				return true;
			}else{
				return false;
			}
        }
    </script>
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
            <table>
                <tr>
                    <td>客户姓名</td>
                    <td><input type="text" name="name" disabled="disabled" 
                    	value="${customer.name }"/></td>
                </tr>
                <tr>
                	<td>联系电话</td>
                	<td><input type="text" name="tel" disabled="disabled" 
                		value="${customer.tel }"/></td>
                </tr>
                <tr>
                	<td>家庭地址</td>
                	<td><input type="text" name="address" disabled="disabled" 
                		value="${customer.address }"/></td>
                </tr>
                
                <tr class="cols2">
                    <td colspan="2" class="info">
                    	<a href="${pageContext.request.contextPath }/PetServlet?m=toAdd&cname=${customer.name}&cid=${customer.id}">
                    	添加新宠物</a>		<!-- 实现添加宠物功能时完善此地址 -->
                    </td>
                </tr>
                
                <tr class="cols2">
                    <td colspan="2" class="info">
                    <%=request.getAttribute("msg") == null ? "" : request.getAttribute("msg") %></td>
                </tr>
            </table>
            
            <hr>
            
            <!-- 以下为客户的宠物信息 -->
            <table class="wide">
            	<thead>
            		<tr>
            			<td colspan="2">宠物信息</td>
            			<td>操作</td>
            		</tr>
            	</thead>
            	
            	<!-- 遍历客户的pets属性，显示客户的所有宠物信息 -->
            	
            	<c:forEach items="${customer.pets}" var="pet">
            		<tr>
            			<td><img alt="此处应有图片" src="${pageContext.request.contextPath }/${pet.photo}" height="64" width="64"></td>
            			
            			<td>姓名:${pet.name },生日${pet.birthdate }</td>
            			
            			<td>
            				<a href="PetServlet?m=delete&petId=${pet.id}&cid=${pet.ownerId}"
            					onclick="return del_confirm();">删除</a>
            				<!-- petId:待删除的宠物id； cid：宠物删除后，以cid即宠物主人id作为参数再次查询客户，返回客户详情页，以便用户即刻看到删除效果 -->
            				<a href="${pageContext.request.contextPath }/VisitServlet?m=showHistory&petId=${pet.id}">浏览病例</a>
            				<a href="${pageContext.request.contextPath }/VisitServlet?m=toAdd&petName=${pet.name}&petId=${pet.id}&cid=${pet.ownerId}">添加病例</a>
            			</td>
            		</tr>
            	</c:forEach>
            	
            	<tr class="cols2">
            		<td colspan="2"><input type="button" value="返回" onclick="history.back(-1);"></td>
            	</tr>
            </table>
    </div>
    <div id="footer"></div>
</div>
</body>
</html>