package stu.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stu.dao.MajorDAO;
import stu.entity.Major;

@WebServlet("/MajorServlet")
public class MajorServlet extends HttpServlet{
	//响应GET请求
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			String m = request.getParameter("m");
			if ("toAdd".equals(m)) { //响应“添加专业”超链接的请求
				toAdd(request, response); //转到添加页major.jsp, 并传递所有专页信息
			}else if ("search".equals(m)) {
				//查询专业
				search(request, response);
			}else if ("delete".equals(m)) {
				//删除专业
				delete(request,response);
			}else if("toUpdate".equals(m)) {
				//转到客户更新页
				toUpdate(request,response);
			}
		}

		

		//响应POST请求
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//通过参数m的值进行区分请求源：查询or添加
			String m = request.getParameter("m");
			if ("search".equals(m)) {
				//查询专业
				search(request, response);
			}else if ("add".equals(m)) {
				//添加专业
				add(request, response);
			}else if ("update".equals(m)) {
				//客户信息更新到DB
				update(request,response);
			}
		}

		



		//添加专业
		private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
			 String name = request.getParameter("name");
		        try{
		        	if (name == null||"".equals(name)) {
						throw new Exception("请输入专业名称");
					}
					
		            if (new MajorDAO().isExist(name)){
		                throw new Exception("该专业已经存在,请重新输入！");
		            }
		            Major major = new Major();
		            major.setName(name);

		            new MajorDAO().save(major);
		          //添加成功则转去查所有专业
		            response.sendRedirect(request.getContextPath()+"/MajorServlet?m=search&majorName=");
		        }catch (Exception e){
		            request.setAttribute("msg",e.getMessage());
		            request.getRequestDispatcher("/majoradd.jsp").forward(request,response);

		        }
		}
		
		private void toUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			try {
				Major major = new MajorDAO().getById(Integer.parseInt(request.getParameter("id")));
				request.setAttribute("major", major);
				request.getRequestDispatcher("/majorupd.jsp").forward(request, response);
			} catch (Exception e) {
				request.setAttribute("msg", e.getMessage());
				request.getRequestDispatcher("majorsearch.jsp").forward(request, response);
			}
		}
		private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			try {
				Major major = new Major();
				int id=Integer.parseInt(request.getParameter("id"));
				major.setId(id);
				major.setName(request.getParameter("name"));
				
				new MajorDAO().modify(major);//更新到数据库
				
				//更新成功则转去查客户详情
				response.sendRedirect(request.getContextPath()+"/MajorServlet?m=search&majorName=");
			} catch (Exception e) {
				request.setAttribute("msg", e.getMessage());
				request.getRequestDispatcher("/Majorsearch.jsp").forward(request, response);
			}
		}
		
		//删除专业
		private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			try {
				int majorId = Integer.parseInt(request.getParameter("majorId"));
				new MajorDAO().delete(majorId);
				
				request.setAttribute("msg", "删除成功");
				//删除成功，再查看当前专业的详细信息
				response.sendRedirect(request.getContextPath()+"/MajorServlet?m=search&majorName=");

			} catch (Exception e) {
				request.setAttribute("msg", e.getMessage());
				response.sendRedirect(request.getContextPath()+"/MajorServlet?m=search&majorName=");
			}
		}

		//查询专业
		private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
			try {
				
				String majorName = request.getParameter("majorName");
				
				List<Major> majors = new MajorDAO().search(majorName);
				
				if (majors.size() == 0) {
					
					request.setAttribute("msg", "没有找到相关专业信息");
					
					request.getRequestDispatcher("/majorsearch.jsp").forward(request, response);
				
				}else { //查到了
					request.setAttribute("majors", majors);
					
					request.getRequestDispatcher("/majorsearch_result.jsp").forward(request, response);
				}
				
			} catch (Exception e) {
				
				request.setAttribute("msg",e.getMessage());
				
				request.getRequestDispatcher("/majorsearch.jsp").forward(request,response);
			}
			
		}

		//转到专业添加页majoradd.jsp
		private void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
			request.getRequestDispatcher("/majoradd.jsp").forward(request,response);
		}

	
}
