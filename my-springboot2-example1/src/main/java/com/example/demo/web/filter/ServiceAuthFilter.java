package com.example.demo.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.util.json.JsonUtil;
import com.example.demo.web.response.CommResponse;

//@Configuration
//@WebFilter(urlPatterns="/service/*", filterName="ServiceAuthFilter")
//@Order(value = 1)
/**
 * 校验服务接口的过滤器
 * @author zhangzhengtao
 *
 */
public class ServiceAuthFilter implements Filter{

	private static Logger logger = LoggerFactory.getLogger(ServiceAuthFilter.class);

	/**
	 * 请求时候调用
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("[filter do]");
		
		HttpServletRequest httpRequest = (HttpServletRequest) request; 
		ResettableServletRequest myRequest = new ResettableServletRequest(httpRequest);

		String requestBody = IOUtils.toString(myRequest.getInputStream(), "UTF-8");
		logger.debug(requestBody);
		
		try {
			//httpRequest.getHeader(name)
			
			chain.doFilter(myRequest, response);
		}catch(Exception e) {
			responseJson(response, CommResponse.getInstances4Fail());
		}
	}
	
	/**
	 * 响应输入指定Json对象
	 * @param response
	 * @param obj
	 */
	protected void responseJson(ServletResponse response, Object obj) {
		response.setCharacterEncoding("UTF-8");  
	    response.setContentType("application/json; charset=utf-8");
	    PrintWriter out = null;
	    try {
			out = response.getWriter();
			out.append(JsonUtil.obj2json(obj));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {  
	        if (out != null) {  
	            out.close();  
	        }  
	    }  
	}
	
	//------------------------------------------------------------------------------------------
	
	/**
	 * 应用启动时候调用
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		logger.debug("[filter init]");
	}
	
	/**
	 * 应用销毁时候调用
	 */
	@Override
	public void destroy() {
		logger.debug("[filter destory]");
	}

}
