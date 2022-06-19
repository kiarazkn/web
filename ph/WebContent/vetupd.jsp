<%@page import="java.util.List"%>
<%@page import="com.mysql.fabric.xmlrpc.base.Value"%>
<%@page import="ph.entity.Speciality"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" 
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>更改医生信息</title>
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
    	<form action="${pageContext.request.contextPath }/VetServlet?m=update&vetId=${param.vetId}" method="post"> 
            <table>
                <tr>
                    <td>客户姓名</td>
                    <td>
                    	<input type="text" name="name" value="${param.vetName }" disabled="disabled"/>
                    </td>
                </tr>
                <tr>
                    <td>专业特长</td>
                    <td>
                    	<select size="5" multiple="multiple" name="specId">
                    		<option disabled="disabled">请选择至少一项</option>
                    		
                    		<%
                    		List<Speciality> allSpecs = (List<Speciality>)request.getAttribute("allSpecs");
                    		//所有专长
                    		
                    		List<Speciality> specs = (List<Speciality>)request.getAttribute("specs");
                    		//医生原具备特长
                    		outloop : for(Speciality spec : allSpecs){//外层循环，遍历所有专长
                    			
                    			for(Speciality selected : specs){//内层循环，遍历医生原本具备的专长
                    				
                    				if(spec.getId()==selected.getId()){
                    					
                    					//若某专长是医生原本具备的专长，则令其为选中状态
                    					out.println("<option value='"+spec.getId()+"' selected='selected'>"+spec.getName()+"</option>");
                    					
                    					continue outloop;//结束本轮循环，继续新一轮外层循环
                    				}
                    			}
                    		//某专长不是医生原本具备的专长，则不是选中状态
                    		out.println("<option value='"+spec.getId()+"'>"+spec.getName()+"</option>");
                    		}
                    		%>
                    	</select>
                    </td>
                </tr>
                
                <tr class="cols2">
                    <td colspan="2">
                    <input type="submit" value="修改"/>
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