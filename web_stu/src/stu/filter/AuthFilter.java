package stu.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import stu.entity.User;

//@WebFilter("*.jsp")
public class AuthFilter implements Filter {
	private static List<String> adminAuthpages = new ArrayList<String>();
	//需以“admin”角色登录成功才能访问的页面列表
	static {
		adminAuthpages.add("/vetsearch.jsp");
		//其他地址陆续添加
	}
	//过滤逻辑
	public void doFilter(ServletRequest request,ServletResponse response,
			FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		HttpSession session = req.getSession(true);
		
		String reqURI = req.getRequestURI();  //获得客户端浏览器请求的URL地址
		
		reqURI = reqURI.substring(reqURI.lastIndexOf("/"));
		// 从完整地址中分离出请求的页面文件夹 /vetsearch.jsp
		
		if (adminAuthpages.contains(reqURI)) { //若请求的地址在需授权列表中
			
			User user = (User) session.getAttribute("user");
			
			if(user == null) {  //没登录，则拦截
				req.setAttribute("msg", "请先登录！");
				req.getRequestDispatcher("/index.jsp").forward(req, response);
				
			}else if ("admin".equals(user.getRole())) {
				//登录了，且为"admin"角色，放行
				chain.doFilter(request, response);
				
			}else {  //登录了，但非“admin”角色，则拦截
				req.setAttribute("msg", "该页面只有管理员才能够访问！");
				req.getRequestDispatcher("/index.jsp").forward(request, response);
			}
			
		}else {  //若请求的地址不在需授权列表中，直接放行   index.jsp  cutindex.jsp
			chain.doFilter(request, response);
		}
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	public void destroy() {
	}
}
