package ph.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


@WebFilter("/*")	//令过滤器对所有地址起作用，所有表单提交给XXXServlet会先经过转码过滤器
public class EncodingFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}
	
	//过滤逻辑
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain)
			throws IOException, ServletException {
	
		// *** chain.doFilter(request,response)	 放行：链  filter1、filter2、 目标地址
		request.setCharacterEncoding("utf-8");//只对POST请求
		
		chain.doFilter(request, response);  //***放行，允许通过过滤器，请求到达过滤器链下一个位置
	}
}
