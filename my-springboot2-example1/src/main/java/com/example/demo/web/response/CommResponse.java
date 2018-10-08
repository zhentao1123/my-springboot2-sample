package com.example.demo.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="响应模型")
public class CommResponse<D> {
	
	public static final String CODE_SUCCEED = "0";
	public static final String CODE_FAIL = "1";
	public static final String MESSAGE_SUCCEED = "succeed";
	public static final String MESSAGE_FAIL = "fail";
	
	@ApiModelProperty(name="code", notes="响应码")
	private String code;
	
	@ApiModelProperty(name="message", notes="响应消息")
	private String message;
	
	@ApiModelProperty(name="data", notes="响应内容")
	private D data;
	
	public CommResponse(String code, String message, D data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public CommResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	public static <D> CommResponse<D> getInstances4Succeed(){
		CommResponse<D> instances = new CommResponse<D>(CODE_SUCCEED, MESSAGE_SUCCEED);
		return instances;
	}
	
	public static <D> CommResponse<D> getInstances4Succeed(D data){
		CommResponse<D> instances = new CommResponse<D>(CODE_SUCCEED, MESSAGE_SUCCEED, data);
		return instances;
	}

	public static <D> CommResponse<D> getInstances4Fail(){
		CommResponse<D> instances = new CommResponse<D>(CODE_FAIL, MESSAGE_FAIL);
		return instances;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public D getData() {
		return data;
	}

	public void setData(D data) {
		this.data = data;
	}

}
