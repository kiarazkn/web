package ph.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ph.dao.PetDAO;
import ph.dao.UserDAO;
import ph.entity.Pet;
import ph.entity.User;

@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
	
	//响应POST请求
	protected void doPost(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException,IOException {
		
		//通过参数m的值进行区分请求源：查询or添加
		String m = request.getParameter("m");
		
		if ("search".equals(m)) {
			//查询客户（基本查询）
			search(request,response);
		}else if ("add".equals(m)) {
			//添加新客户
			add(request, response);
		}else if ("update".equals(m)) {
			//客户信息更新到DB
			update(request,response);
		}else if ("showDetail".equals(m)) {
			//查看客户详情信息
			showDetail(request, response);
		}
	}
	
	//响应GET请求
	protected void doGet(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException,IOException {
		
		//通过参数m的值进行区分请求源：查询or添加
		String m = request.getParameter("m");
		
		if ("showDetail".equals(m)) {
			//查看客户详情信息
			showDetail(request,response);
		}else if ("search".equals(m)) {
			//查询客户（基本查询）
			search(request, response);
		}else if("toUpdate".equals(m)) {
			//转到客户更新页
			toUpdate(request,response);
		}else if ("delete".equals(m)) {
			//删除客户
			delete(request,response);
		}
	}
	
	//删除客户
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		try {
			int id=Integer.parseInt(request.getParameter("cid"));
			new UserDAO().delete(id);//根据id删除客户
			
			//更新成功则转去查所有客户
			response.sendRedirect(request.getContextPath()+"/CustomerServlet?m=search&customerName=");
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
	}

	//客户信息更新到DB
	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		try {
			User customer = new User();
			int id=Integer.parseInt(request.getParameter("id"));
			customer.setId(id);
			customer.setTel(request.getParameter("tel"));
			customer.setAddress(request.getParameter("address"));
			
			new UserDAO().modify(customer);//更新到数据库
			
			//更新成功则转去查客户详情
			request.getRequestDispatcher("CustomerServlet?m=showDetail&cid="+id).forward(request, response);
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
	}
	
	//转到客户更新页
	private void toUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		/*
		 * 根据cid查客户
		 * 客户对象传至客户更新页customerupd.jsp
		 */
		try {
			User customer = new UserDAO().getById(Integer.parseInt(request.getParameter("cid")));
			request.setAttribute("customer", customer);
			request.getRequestDispatcher("/customerupd.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("customersearch.jsp").forward(request, response);
		}
	  }
	 
	//客户基本信息查询
	private void search(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		
		try {
			
			/*
			 * 取表单中的客户名字的关键字
			 * 调用UserDAO中的searchCustomer(关键字)
			 * 结果转发到结果页customersearch_result.jsp
			 */
			
			List<User> customers = new UserDAO().searchCustomer(request.getParameter("customerName"));
		
			if (customers.size()==0) {//按名字关键字没查到
				request.setAttribute("msg", "没有找到相关客户信息");
				request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
			
			}else {//查到了
				request.setAttribute("customers", customers);
				request.getRequestDispatcher("/customersearch_result.jsp").forward(request, response);
			}
		
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
	}
	
	//查看客户详情信息
	private void showDetail(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		try {
			
			UserDAO userDAO=new UserDAO();
			PetDAO petDAO=new PetDAO();
			
			int ownerId=Integer.valueOf(request.getParameter("cid"));
			//从超链接获取待查看详情的客户id
			
			User customer = userDAO.getById(ownerId);  //根据客户id查询客户
			
			List<Pet> pets = petDAO.getPetsByOwnerId(ownerId);  //根据客户id查询其宠物
			
			customer.setPets(pets);  //将查到的宠物集合绑定到客户的pets属性上
			
			request.setAttribute("customer", customer);
			//客户信息（含宠物信息）绑定传递给customerdetail.jsp页面
			
			request.getRequestDispatcher("/customerdetail.jsp").forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
	}
	
	//添加新客户
	private void add(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		
		try {
			User customer = new User();
			
			//表单获取客户基本信息，封装到customer对象
			customer.setName(request.getParameter("name"));
			customer.setTel(request.getParameter("tel"));
			customer.setAddress(request.getParameter("address"));
			
			customer.setPwd("123456"); //指定默认密码
			customer.setRole("customer"); //客户的role值固定为customer
			
			new UserDAO().save(customer); //客户信息入库
			
			request.setAttribute("msg", "添加用户成功");
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		} catch (Exception e) {
			
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/customersearch.jsp").forward(request, response);
		}
	}
}
