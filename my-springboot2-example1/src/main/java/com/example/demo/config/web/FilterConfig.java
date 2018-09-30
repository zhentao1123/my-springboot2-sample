package com.example.demo.config.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean serviceAuthFilterRegistration() {
		// 配置无需过滤的路径或者静态资源，如：css，imgage等
		StringBuffer excludedUriStr = new StringBuffer();
//		excludedUriStr.append("/login/*");
//		excludedUriStr.append(",");
//		excludedUriStr.append("/favicon.ico");
//		excludedUriStr.append(",");
//		excludedUriStr.append("/js/*");

		FilterRegistrationBean registration = new FilterRegistrationBean();
	//registration.setFilter(new ServiceAuthFilter());
		registration.addUrlPatterns("/service/*");
		//registration.addInitParameter("excludedUri", excludedUriStr.toString());
		registration.setName("ServiceAuthFilter");
		registration.setOrder(1);
		
		return registration;
	}
	
}
