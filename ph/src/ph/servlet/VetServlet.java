package ph.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ph.dao.SpecialityDAO;
import ph.dao.VetDAO;
import ph.entity.Speciality;
import ph.entity.Vet;

@WebServlet("/VetServlet")
public class VetServlet extends HttpServlet {
	
	//响应GET请求
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String m = request.getParameter("m");
		if ("toAdd".equals(m)) { //响应“添加医生”超链接的请求
			toAdd(request, response); //转到添加页vetadd.jsp, 并传递所有专长信息
		}else if ("toUpdate".equals(m)) {
			//转到医生信息修改页，允许修改医生的专长信息
			toUpdate(request,response);
		}else if ("search".equals(m)) {
			//查询医生
			search(request, response);
		}
	}

	//响应POST请求
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//通过参数m的值进行区分请求源：查询or添加
		String m = request.getParameter("m");
		
		if ("search".equals(m)) {
			//查询医生
			search(request, response);
			
		}else if ("add".equals(m)) {
			//添加医生 (vetadd.jsp页表单提交的请求)
			
			add(request, response);
			
		}else if ("update".equals(m)) {
			//修改医生专业信息
			update(request,response);
		}
	}
	
	//仅供当前类调用的
	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		
		try {
			/*
			 * 取表单的参数，vetName specName
			 * 调用VetDAO中search(vetName,specName)
			 * 结果传递给结果页  vetsearch_result.jsp
			 */
			String vetName = request.getParameter("vetName");
			String specName = request.getParameter("specName");
			
			//VetDAO vetDAO = new VetDAO();
			
			List<Vet> vets = new VetDAO().search(vetName, specName);
			
			if (vets.size() == 0) {
				
				request.setAttribute("msg", "没有找到相关医生信息");
				
				request.getRequestDispatcher("/vetsearch.jsp").forward(request, response);
			
			}else { //查到了
				request.setAttribute("vets", vets);
				
				request.getRequestDispatcher("/vetsearch_result.jsp").forward(request, response);
			}
			
		} catch (Exception e) {
			
			request.setAttribute("msg",e.getMessage());
			
			request.getRequestDispatcher("/vetsearch.jsp").forward(request,response);
		}
		
	}
	
	//转到医生添加页vetadd.jsp, 并传递所有专长信息
	private void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			request.setAttribute("specs", new SpecialityDAO().getAll());
			request.getRequestDispatcher("/vetadd.jsp").forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/vetsearch.jsp").forward(request, response);
		}
		
	}
	
	//添加医生信息入库：除医生基本信息外，还包括医生的专业信息
	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String vetName = request.getParameter("name");
			if (vetName == null||"".equals(vetName)) {
				throw new Exception("请输入医生姓名");
			}
			
			String specIds[] = request.getParameterValues("specId");
			//专长下拉列表是多选的
			if (specIds == null||specIds.length==0) {
			throw new Exception("请选择至少一项专业特长");
			}
			
			Vet vet = new Vet();
			
			vet.setName(request.getParameter("name"));
			
			for (int i = 0; i < specIds.length; i++) {  //封装下拉列表中选中的医生专长信息（专长id）
				Speciality spec = new Speciality();
				spec.setId(Integer.valueOf(specIds[i]));
				vet.getSpecs().add(spec);
			}
			
			new VetDAO().save(vet);		//调用VetDAO的save()方法实现医生入库
			
			request.setAttribute("msg", "操作成功");
			request.getRequestDispatcher("/vetsearch.jsp").forward(request,
					response);
			
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			//异常发生时，反馈异常消息，并转到医生添加页
			toAdd(request, response);
		}
	}

	//转到医生修改页vetupd.jsp，并传递专长信息
	private void toUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			//所有专长
			request.setAttribute("allSpecs", new SpecialityDAO().getAll());
			
			//医生的原有专长信息
			int vetId = Integer.parseInt(request.getParameter("vetId"));
			List<Speciality> specs = new SpecialityDAO().getSpecialitiesByVetId(vetId);
			
			request.setAttribute("specs", specs);
			
			//请求转发到医生修改页vetupd.jsp
			request.getRequestDispatcher("/vetupd.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/vetsearch.jsp").forward(request, response);
		}
	}

	//修改医生专业信息
	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			String specIds[] = request.getParameterValues("specId");
			//获取选中的新专长
			if(specIds == null || specIds.length==0) {
				throw new Exception("请选择至少一项专业特长");
			}
			
			Vet vet = new Vet();
			//封装医生id
			vet.setId(Integer.parseInt(request.getParameter("vetId")));
			
			//封装医生新的专长信息（专长id）
			for(int i = 0; i < specIds.length; i++) {
				Speciality spec = new Speciality();
				spec.setId(Integer.valueOf(specIds[i]));
				vet.getSpecs().add(spec);
			}
			
			new VetDAO().modify(vet);//医生专业信息更新到数据库
			
			//重新查询所有医生级其专长
			//注意：重定向是get请求，VetServlet中应在doGet()方法中添加针对m=search的处理分支
			response.sendRedirect(request.getContextPath()+"/VetServlet?m=search&vetName=&specName=");
			
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/vetsearch.jsp").forward(request, response);
		}
	}
}
