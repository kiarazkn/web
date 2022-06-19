package ph.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ph.dao.VetDAO;
import ph.dao.VisitDAO;
import ph.entity.Vet;
import ph.entity.Visit;

@WebServlet("/VisitServlet")
public class VisitServlet extends HttpServlet {

	//响应GET请求
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
			
		String m = request.getParameter("m");
			
		if ("toAdd".equals(m)) { 
				
			toAdd(request, response); 	
		}else if ("showHistory".equals(m)) {
			//浏览宠物病例
			showHistory(request, response);
		}
	}
	
	//响应POST请求
		protected void doPost(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException{
				
			String m = request.getParameter("m");
				
			if ("add".equals(m)) { 
				//添加病例
				add(request,response);
			}
		}
	
	
	//转到病例添加页visitadd.jsp
	private void toAdd(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		try {
			
			VetDAO vetDAO = new VetDAO();
			List<Vet> vets = vetDAO.getAll(); //查询所有医生
			
			//将医生集合vets转到病例添加页面visitadd.jsp中，供添加病例时选择医生用
			request.setAttribute("vets", vets);
			
			//请求转发到visitadd.jsp,petId、petName、cid都会随request对象传递过去
			request.getRequestDispatcher("/visitadd.jsp").forward(request, response);
		
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
		
	}

	//添加病例
	private void add(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		
		try {
			Visit visit = new Visit();
			
			visit.setPetId(Integer.parseInt(request.getParameter("petId")));
			visit.setVetId(Integer.parseInt(request.getParameter("vetId")));
			visit.setDescription(request.getParameter("description"));
			visit.setTreatment(request.getParameter("treatment"));
			
			new VisitDAO().save(visit); //调save()方法实现病例入库
			
			//病例添加成功后，再重新查看当前客户详细信息
			response.sendRedirect(request.getContextPath()
					+"/CustomerServlet?m=showDetail&cid=" + request.getParameter("cid"));
			
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
	}

	//浏览宠物病例
	private void showHistory(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		try {
			VisitDAO visitDAO = new VisitDAO();
			List<Visit> visits = visitDAO.getVisitsByPetId(Integer.parseInt(request.getParameter("petId")));
		
			request.setAttribute("visits", visits);
			
			if (visits.size() == 0) {
				request.setAttribute("msg", "没有找到历史病例");
			}
			
			request.getRequestDispatcher("/visitsearch_result.jsp").forward(request, response);
		
		} catch (Exception e) {
			
			request.setAttribute("msg", e.getMessage());
			
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
	}
}
