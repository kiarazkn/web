package ph.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import ph.dao.SpecialityDAO;

import ph.entity.Speciality;


@WebServlet("/SpecServlet")
public class SpecServlet extends HttpServlet{

	//响应POST请求
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m = request.getParameter("m");
		if ("add".equals(m)) {
			//添加专业
			add(request, response);
		}else if ("search".equals(m)) {
			//查询专业
			search(request, response);
		}
	}
	
	//响应GET请求
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m = request.getParameter("m");
		if ("delete".equals(m)) { 
			//删除专业
			delete(request, response); 
		} else if ("toAdd".equals(m)) { 
			//转到专业添加页specadd.jsp
			toAdd(request,response);
		}else if ("search".equals(m)) {
			//查询专业
			search(request, response);
		}
	}

	//查询专业
	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		try {
			
			List<Speciality> specs = new SpecialityDAO().search(request.getParameter("specName"));
//			List<Speciality> specs = new SpecialityDAO().getAll();
			
			if (specs.size()==0) {//按专业名称关键字没查到
				request.setAttribute("msg", "没有找到相关专业信息");
				request.getRequestDispatcher("/specsearch.jsp").forward(request, response);
			
			}else {//查到了
				request.setAttribute("specs", specs);
				request.getRequestDispatcher("/specsearch_result.jsp").forward(request, response);
			}
		
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/specsearch.jsp").forward(request, response);
		}
		
	}
	
	//添加专业
//	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//		try {
//			String specName = request.getParameter("name");
//			if (specName == null||"".equals(specName)) {
//				throw new Exception("请输入专业名称");
//			}
//			
//			Speciality spec = new Speciality();
//			
//			spec.setName(request.getParameter("name"));
//			
//			new SpecialityDAO().save(spec);		//调用SpecDAO的save()方法实现专业入库
//			
//			request.setAttribute("msg", "操作成功");
//			response.sendRedirect(request.getContextPath()+"/SpecServlet?m=search&specName=");
//			
//		} catch (Exception e) {
//			request.setAttribute("msg", e.getMessage());
//			//异常发生时，反馈异常消息，并转到专业添加页
//			toAdd(request, response);
//		}
//	}
	private void add(HttpServletRequest request, HttpServletResponse response)
        throws ServletException,IOException{
        String name = request.getParameter("name");
        try{
            if (new SpecialityDAO().isExist(name)){
                throw new Exception("该专业已经存在,请重新输入！");
            }

            Speciality spec = new Speciality();
            spec.setName(name);

            new SpecialityDAO().save(spec);

            response.sendRedirect(request.getContextPath()+"/specsearch.jsp");
        }catch (Exception e){
            request.setAttribute("msg",e.getMessage());
            request.getRequestDispatcher("/specadd.jsp").forward(request,response);

        }


    }

	//转到专业添加页specadd.jsp
	private void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/specadd.jsp").forward(request,response);
	}

	//删除专业
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			int specId=Integer.parseInt(request.getParameter("specId"));
			new SpecialityDAO().delete(specId);//根据id删除专业
			//删除成功则转去查所有专业
			response.sendRedirect(request.getContextPath()+"/SpecServlet?m=search&specName=");
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/specsearch.jsp").forward(request, response);
		}
	}
}
