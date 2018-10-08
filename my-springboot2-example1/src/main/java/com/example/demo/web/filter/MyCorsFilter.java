package com.example.demo.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 全局跨域设置过滤器
 */
//@Configuration
//@WebFilter(urlPatterns="/*")
//@Order(value = 1)
public class MyCorsFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(MyCorsFilter.class);
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		
		chain.doFilter(request, response);
	}

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
