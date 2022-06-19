package ph.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import ph.dao.PetDAO;
import ph.entity.Pet;

@MultipartConfig  //表示此Servlet支持文件上传

@WebServlet("/PetServlet")
public class PetServlet extends HttpServlet {

		//响应GET请求
		protected void doGet(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException{
			
			String m = request.getParameter("m");
			
			if ("delete".equals(m)) { 
				//删除宠物
				delete(request, response); 
				
			} else if ("toAdd".equals(m)) { 
				//转到宠物添加页petadd.jsp
				toAdd(request,response);
			}
		}
		
		//响应POST请求
		protected void doPost(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
					
			String m = request.getParameter("m");
			
			if ("add".equals(m)) {
				//添加宠物
				add(request, response);
			}
		}
		
		//删除宠物
		private void delete(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException{
			
			try {
				
				int petId = Integer.parseInt(request.getParameter("petId"));
				new PetDAO().delete(petId);
				
				request.setAttribute("msg", "删除成功");
				//删除成功，再查看当前客户的详细信息
				request.getRequestDispatcher (
					"/CustomerServlet?m=showDetail&cid="
						+ request.getParameter("cid")).forward(request, response);

			} catch (Exception e) {
				request.setAttribute("msg", e.getMessage());
				request.getRequestDispatcher(
						"/CustomerServlet?m=showDetail&cid="
								+ request.getParameter("cid")).forward(request, response);
			}
		}

		//转到宠物添加页petadd.jsp
		private void toAdd(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException{
			
			request.getRequestDispatcher("/petadd.jsp").forward(request,response);
			//因使用请求转发方式，request对象不变
			//"添加新宠物"超链接请求中带的cid、cname参数会随请求对象传递到petadd.jsp页面
		}
		
		//添加宠物
		private void add(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException{
			
			try {
				Part part = request.getPart("photo");
				
				String filename = getFileName(part);	//调用自定义的getFileName()方法：从part中获取待上传文件的文件名
				
				String photo;	//用于存上传后的照片地址（相对地址），上传后的照片地址格式：photo/当前系统时间的毫秒值.扩展名
				
				String real_photo;	//用于存上传后的照片绝对地址（磁盘地址）
				
				if (filename != null) {
					
					long currentTimeMillis = System.currentTimeMillis();
					//获得当前系统时间的毫秒值（从1970-01-01至今经过了多少毫秒）
					
					photo = "photo/" + currentTimeMillis + filename.substring(filename.lastIndexOf("."));
					//生成上传之后的照片地址（相对地址）； photo/当前系统时间的毫秒值.拓展名
					
					real_photo = getServletContext().getRealPath("/") + "/" + photo;
					
					part.write(real_photo);		//上传文件至磁盘指定位置
				}else {
					photo = "photo/default.jpg";	//未选择上传文件，则使用默认图片（需准备好）
				}
				Pet pet = new Pet();
				pet.setName(request.getParameter("name"));
				pet.setBirthdate(request.getParameter("birthdate"));
				pet.setPhoto(photo);	//写入数据库的路径使用相对路径photo，而不是磁盘地址real_photo
				pet.setOwnerId(Integer.parseInt(request.getParameter("cid")));
				
				new PetDAO().save(pet);  //完成添加到数据库
				
				request.setAttribute("msg", "添加成功");
				
				//添加成功，转去查看当前客户的详细信息，这里用了重定向，注意地址正确
				response.sendRedirect(request.getContextPath() + "/CustomerServlet?m=showDetail&cid="
						+ pet.getOwnerId());
			} catch (Exception e) {
				request.setAttribute("msg", e.getMessage());
				
				//转去查看当前客户基本信息，这里用了重定向，注意地址正确
				response.sendRedirect(request.getContextPath() + "/CustomerServlet?m=search&customerName=");
			}
		}
		
		private String getFileName(Part part) {
			
			// 获取header信息中的content-disposition,如果为文件，则可以从其中提取出文件名
			String contentDesc = part.getHeader("content-disposition");
			
			String fileName = null;
			
			Pattern pattern = Pattern.compile("filename=\".+\"");
			//定义匹配模式为：filename="." (其中 . 可以有1个或多个)
			
			Matcher matcher = pattern.matcher(contentDesc);
			//创建匹配器，指定需要与pattern进行匹配的字符串是contentDesc
			
			if (matcher.find()) {  //若找到了匹配的字符序列
				
				fileName = matcher.group();
				//获取匹配的字符序列
				
				fileName = fileName.substring(10,fileName.length() - 1);
				//从类似 filename="tom.jpg" 的字符序列中截取文件名的那部分，这里即tom.jpg
			}
			return fileName;  //返回解析出来的文件名
		}
}
