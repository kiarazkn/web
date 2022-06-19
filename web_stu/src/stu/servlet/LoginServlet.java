package stu.servlet;
import stu.dao.UserDAO;
import stu.entity.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{

	 protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        doPost(req,resp);
	    }
	 
	 //响应POST请求
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        /*
	        从登录表单获取用户输入的验证码
	        判断验证码是否正确：
	                     错误：转到index.jsp.并报错   request.setAttribute("msg","验证码输入错误！")
	                     正确：判断登录用户是否存在  （UserDAO    getUserByName("用户输入的名字")
	                           不存在 null，转到index.jsp.并报错    "用户名不存在"
	                           存在   user非空：判断用户输入的密码是否正确
	                                  密码不正确 用户输入的密码和user.getPwd()比对失败：转到index.jsp.并报错 "密码错误"
	                                  密码正确：将登录成功的user存入session，判断用户角色role
	                          role值为admin，转到majorsearch.jsp
	                          role值为student，转到stuindex.jsp
	        */
	    	
	    	//req.setCharacterEncoding("UTF-8");	//解决中文乱码问题
	    	
	        String msg=null;    //存报错信息
	        String url=null;    //存跳转地址
	        HttpSession session = req.getSession(true);
	        String realcode = (String) session.getAttribute("realcode");    //realcode正确的验证码
	        String inputcode = req.getParameter("checkcode");               //inputcode用户输入的验证码
	        if(realcode.equalsIgnoreCase(inputcode)) { //验证码是正确的
	            //下一步：判断用户名在DB中有没有
	            String name = req.getParameter("name");		//乱码!
	            User user = new UserDAO().getUserByName(name);
	            System.out.print(name);
	            if (user == null) { //没查到，用户名不存在
	                msg = "用户名不存在";
	                url = "/index.jsp";
	            }else if ( !user.getPwd().equals(req.getParameter("pwd")) ) {  //用户名查到了，进一步看密码
	                //密码不正确
	                msg="密码错误！";
	                url="/index.jsp";
	            }else{  //密码也正确，成功登录
	                session.setAttribute("user",user);
	                if ("admin".equals(user.getRole())) {
	                    url="/majorsearch.jsp";
	                }else if ("student".equals(user.getRole())) {
	                    url="/stusearch.jsp";
	                }
	            }
	        }else{ //验证码不一致
	            msg="验证码输入错误！";
	            url="/index.jsp";
	        }
	        req.setAttribute("msg",msg);
	        req.getRequestDispatcher(url).forward(req,resp);
	    }
}
