<%@ page language="java" contentType="text/html;charset=UTF-8" 
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>首页</title>
  <%-- 当前WEB应用根目录/css/styles.css--%>
 
  <link rel="stylesheet"href="${pageContext.request.contextPath }/css/styles.css">
   <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
  <script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div id="container">
  <div id="header">
    <h1>学生管理系统</h1>
  </div>
  <div id="content">
    <form action="LoginServlet" method="post">
      <table>
        <tr>
          <td>用户名</td>
          <td><input class="form-control" style="width:380px;" type="text" name="name"/></td>
        </tr>
        <tr>
          <td>密码</td>
          <td><input class="form-control" style="width:380px;" type="password" name="pwd"/></td>
        </tr>
        <tr>
          <td>验证码</td>
          <td><input class="form-control" style="width:380px;"  type="text" name="checkcode"/></td>
        </tr>
        <tr>
          <td>点击刷新</td>
          <td><input type="image" name="img-code" id="img-code"
                     alt="看不清，点击换图"
                     src="CheckCode"
                     onclick="javascript:this.src=this.src+'?';return false;"></td>
                    <%-- javascript:this.src=this.src+'?';保证每点击一次验证码能换一个图
                    return false; 不提交请求，只有点submit按钮的时候才提交请求--%>
        </tr>
        <tr class="cols2">
          <td colspan="2"><input class="btn btn-info" type="submit" value="登录"/>
            <input class="btn btn-info" type="reset" value="重置" /></td>
        </tr>
        <tr class="cols2">
          <%-- 报错信息的显示--%>
          <td colspan="2" class="info">
            <%=request.getAttribute("msg")==null?"":request.getAttribute("msg") %>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <div id="footer"></div>
</div>
</body>
</html>
