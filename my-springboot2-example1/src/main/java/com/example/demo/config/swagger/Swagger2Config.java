package com.example.demo.config.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.async.DeferredResult;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Bean
	public Docket webV1Docket() {
		// 全局配置请求参数
//		List<Parameter> operationParameters = new ArrayList<Parameter>();
//		operationParameters.add(
//				new ParameterBuilder().name("authCheck").description("Check Auth or Not?")
//				.modelRef(new ModelRef("boolean")).parameterType("header")
//				.required(false).build());
		return new Docket(DocumentationType.SWAGGER_2).groupName("test-v1")
				// .genericModelSubstitutes(DeferredResult.class)
				// .useDefaultResponseMessages(false)
				// .forCodeGeneration(true)
				// .pathMapping("/springboot")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.demo.web.v1"))//api过滤，匹配包
				//.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))// api过滤，匹配类注解
				//.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))//api过滤，匹配方法注解
				// .paths(PathSelectors.any())
				// .paths(Predicates.not(PathSelectors.regex("/api/.*")))//api过滤，路径过滤
				.build()
				//.globalOperationParameters(operationParameters) // 全局配置请求参数
				// .directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class) //
				// Java8时间类型替换方案
				// .directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class)
				.apiInfo(apiInfo());
	}
	
	/*
	@Bean
	public Docket weixinOpenDocket() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("weixin-open")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.demo.web.xx"))//api过滤，匹配包
				.build()
				.apiInfo(apiInfo());
	}
	*/

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("API标题位").description("API描述位").termsOfServiceUrl("服务URL的规范描述")
				.contact(new Contact("接口联系人", "联系人网站", "联系人邮箱")).version("1.0").build();
	}
}
