<%@page import="java.util.List"%>
<%@page import="com.mysql.fabric.xmlrpc.base.Value"%>
<%@page import="ph.entity.Speciality"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" 
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加医生信息</title>
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
    
        <form action="${pageContext.request.contextPath }/VetServlet?m=add" method="post">
        <!-- 通过m参数传递请求的具体操作（增删改查） -->    
            <table>
                <tr>
                    <td>医生姓名</td>
                    <td><input type="text" name="name"/></td>
                </tr>
                <tr>
                    <td>专业特长</td>
                    <td>
                    	<select size="5" multiple="multiple" name="specId">
                    	<!-- 使用支持多选的下拉列表显示诊所的所有专长信息，供用户选择，一个医生可以具备多个专业特长 -->	
                    		<option disabled="disabled">请至少选择一项</option>
                    		<%
                    			List<Speciality> specs = (List<Speciality>) request.getAttribute("specs");
                    		
                    		//遍历所有专长的集合，每一个专长用一个选项option列出来，value用专长id（这是要写入数据库的），显示用专长name
                    			for (Speciality s : specs) {
                    		%>
                    			<option value="<%=s.getId()%>"><%=s.getName() %></option>
                    		<%
                    			}
                    		%>			
                    	</select>
                    </td>
                </tr>
                
                <tr class="cols2">
                    <td colspan="2">
                    <input type="submit" value="保存"/>
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