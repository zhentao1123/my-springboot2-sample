package com.example.demo.config.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;


@ServletComponentScan
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport { 
	
	@Resource(name="customObjectMapper")
	CustomObjectMapper customObjectMapper;
	
	@Autowired
	RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	
	/**
	 * 资源设置
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//为swagger-ui设置资源映射
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	/**
	 * 拦截器设置
	 */
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
		//registry.addInterceptor(new APIAuthInterceptor())
		//		.addPathPatterns("/api/**")
		//		.excludePathPatterns("/api/*/tenant/gettoken"); 
		//registry.addInterceptor(new VisaInterceptor()).addPathPatterns("/visa/**");
		super.addInterceptors(registry);
    }
	
	/**
	 * 跨域设置
	 */
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            //.allowedOrigins("http://domain2.com")
            .allowedOrigins("*")
            //.allowedMethods("POST","GET")
            //.allowedHeaders("header1", "header2", "header3")
            //.exposedHeaders("header1", "header2")
            //.allowCredentials(false)
            //.maxAge(3600)
            ;
    }
	
	@PostConstruct
    public void init() {
        List<HttpMessageConverter<?>> messageConverters = requestMappingHandlerAdapter.getMessageConverters();
        for (HttpMessageConverter<?> messageConverter : messageConverters) {
            if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter m = (MappingJackson2HttpMessageConverter) messageConverter;
                m.setObjectMapper(customObjectMapper);
            }
        }
    }
}