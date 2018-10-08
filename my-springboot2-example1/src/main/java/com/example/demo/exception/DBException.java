package com.example.demo.exception;

@SuppressWarnings("serial")
public class DBException extends BaseException{

	public DBException(String code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public DBException(String code, String message) {
		super(code, message);
	}

	public DBException(Throwable cause) {
		super(cause);
	}

}
