package com.example.demo.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

@SuppressWarnings("serial")
public class BaseException extends Exception{
	protected String code;
	protected String message;
	
	public BaseException() {
		super();
	}

	public BaseException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
	
	public BaseException(String code, String message, Throwable cause) {
		super(cause);
		this.code = code;
		this.message = message;
	}
	
	public final String getStractTrace() {
		return getStractTrace(this);
	}
	
	/**
	 * 获取StractTrace文本
	 * @param e
	 * @return
	 */
	public static final String getStractTrace(Exception e) {
		StringWriter sw = new StringWriter(); 
        e.printStackTrace(new PrintWriter(sw, true)); 
        String strs = sw.toString(); 
        return strs;
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
	
}
