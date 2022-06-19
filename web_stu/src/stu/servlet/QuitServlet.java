package stu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/QuitServlet")
public class QuitServlet extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		req.getSession(true).invalidate();	//注销session
		
		resp.sendRedirect("index.jsp");	//重定向(超链接)到登录页
		//相对地址：不要/ ； 绝对地址：要加/，必须知道/是什么含义：重定向 /服务器根  ；  请求转发：/当前web应用根目录
		//req.getContextPath()的作用是获取当前WEB应用根目录地址
		//req.getRequestDispatcher("/index.jsp").forward(req, resp);  //当前WEB应用根目录
	}
	
	
}
