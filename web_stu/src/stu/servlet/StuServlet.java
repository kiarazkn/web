package stu.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stu.dao.MajorDAO;
import stu.dao.StuDAO;
import stu.entity.Major;
import stu.entity.Stu;

@WebServlet("/StuServlet")
public class StuServlet extends HttpServlet{
	//响应GET请求
			protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
				String m = request.getParameter("m");
				if ("toAdd".equals(m)) { //响应“添加专业”超链接的请求
					toAdd(request, response); //转到添加页major.jsp, 并传递所有专页信息
				}else if ("search".equals(m)) {
					//查询学生
					search(request, response);
				}else if ("delete".equals(m)) {
					//删除学生
					delete(request,response);
				
				}else if ("toUpdate".equals(m)) {
					//转到信息修改页，允许修改专业信息
					toUpdate(request,response);
				}
			}


			//响应POST请求
			protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				//通过参数m的值进行区分请求源：查询or添加
				String m = request.getParameter("m");
				if ("search".equals(m)) {
					//查询学生
					search(request, response);
				}else if ("add".equals(m)) {
					//添加学生
					add(request, response);
				}else if ("update".equals(m)) {
					//信息更新到DB
					update(request,response);
				}
			}

			//查询学生
			private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
				try {
					
					String stuName = request.getParameter("stuName");
					String majorName = request.getParameter("majorName");
					
					List<Stu> stus = new StuDAO().search(stuName, majorName);
					
					if (stus.size() == 0) {
						
						request.setAttribute("msg", "没有找到相关学生信息");
						
						request.getRequestDispatcher("/stusearch.jsp").forward(request, response);
					
					}else { //查到了
						request.setAttribute("stus", stus);
						
						request.getRequestDispatcher("/stusearch_result.jsp").forward(request, response);
					}
					
				} catch (Exception e) {
					
					request.setAttribute("msg",e.getMessage());
					
					request.getRequestDispatcher("/stusearch.jsp").forward(request,response);
				}
			}

			private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
				try {
					int stuId = Integer.parseInt(request.getParameter("stuId"));
					new StuDAO().delete(stuId);
					
					request.setAttribute("msg", "删除成功");
					//删除成功，再查看当前专业的详细信息
					response.sendRedirect(request.getContextPath()+"/StuServlet?m=search&stuName=&majorName=");


				} catch (Exception e) {
					request.setAttribute("msg", e.getMessage());
					response.sendRedirect(request.getContextPath()+"/StuServlet?m=search&stuName=&majorName=");
				}
			}
			
			

			//转到学生添加页stuadd.jsp
			private void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
				try {
					
					request.setAttribute("majors", new MajorDAO().getAll());
					request.getRequestDispatcher("/stuadd.jsp").forward(request, response);
					
				} catch (Exception e) {
					request.setAttribute("msg", e.getMessage());
					request.getRequestDispatcher("/stusearch.jsp").forward(request, response);
				}
			}


			//添加学生
			private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
				
				try {
					String stuName = request.getParameter("name");
					String stuGender = request.getParameter("gender");
					String stuBirthday = request.getParameter("birthday");
					String stuTel = request.getParameter("tel");
					if (stuName == null||"".equals(stuName)) {
						throw new Exception("请输入学生姓名");
					}else if (stuGender == null||"".equals(stuGender)) {
						throw new Exception("请输入学生性别");
					}else if (stuBirthday == null||"".equals(stuBirthday)) {
						throw new Exception("请输入学生生日");
					}else if (stuTel == null||"".equals(stuTel)) {
						throw new Exception("请输入学生联系方式");
					}
					
					String majorIds[] = request.getParameterValues("majorId");
					//专长下拉列表是多选的
					if (majorIds == null||majorIds.length==0) {
					throw new Exception("请选择至少一项专业特长");
					}
					
					Stu stu = new Stu();
					
					stu.setName(request.getParameter("name"));
					stu.setGender(request.getParameter("gender"));
					stu.setBirthday(request.getParameter("birthday"));
					stu.setTel(request.getParameter("tel"));
					
					for (int i = 0; i < majorIds.length; i++) {  //封装下拉列表中选中的医生专长信息（专长id）
						Major major = new Major();
						major.setId(Integer.valueOf(majorIds[i]));
						stu.getMajors().add(major);
					}
					
					new StuDAO().save(stu);		//调用VetDAO的save()方法实现医生入库
					
					request.setAttribute("msg", "操作成功");
					response.sendRedirect(request.getContextPath()+"/StuServlet?m=search&stuName=&majorName=");
					
				} catch (Exception e) {
					request.setAttribute("msg", e.getMessage());
					//异常发生时，反馈异常消息，并转到医生添加页
					toAdd(request, response);
				}
			}
			
			private void toUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{

				try {

					Stu stu = new StuDAO().getById(Integer.parseInt(request.getParameter("id")));

					request.setAttribute("stu", stu);
					request.getRequestDispatcher("/stuupd.jsp").forward(request, response);
				} catch (Exception e) {
				request.setAttribute("msg", e.getMessage());
					request.getRequestDispatcher("stusearch.jsp").forward(request, response);
				}
			}

			private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
				
				try {
					Stu stu = new Stu();
					int id=Integer.parseInt(request.getParameter("id"));
					stu.setId(id);
					stu.setName(request.getParameter("name"));
					stu.setGender(request.getParameter("gender"));
					stu.setBirthday(request.getParameter("birthday"));
					stu.setTel(request.getParameter("tel"));
					
					new StuDAO().modify(stu);//更新到数据库
					
					//更新成功则转去查客户详情
					response.sendRedirect(request.getContextPath()+"/StuServlet?m=search&stuName=&majorName=");
				} catch (Exception e) {
					request.setAttribute("msg", e.getMessage());
					request.getRequestDispatcher("/stusearch.jsp").forward(request, response);
				}
			}	

}