package com.example.demo.exception;

@SuppressWarnings("serial")
public class BizException extends BaseException{
	
	public BizException() {}
	
	public BizException(Exception e) {
		super(e);
	}
	
	public BizException(String code, String message) {
		super(code, message);
	}
	
	public BizException(BizErrEnum bizErrEnum, Exception e) {
		super(bizErrEnum.getCode(), bizErrEnum.getMessage(), e);
	}
	
	public BizException(String code, String message, Exception e) {
		super(code, message, e);
	}
	
	public BizException(BizErrEnum bizErrEnum) {
		super(bizErrEnum.getCode(), bizErrEnum.getMessage());
	}

	public static BizException getBizException(Exception e) {
		return new BizException(e);
	}
	
	public static BizException getCommException(Exception e) {
		return new BizException(BizException.BizErrEnum.COMM_ERR, e);
	}
	
	public static BizException getCommException() {
		return new BizException(BizException.BizErrEnum.COMM_ERR);
	}
	
	public static BizException getParamException() {
		return new BizException(BizException.BizErrEnum.PARAM_ERR);
	}
	
	public static BizException getParamException(String message) {
		return new BizException(BizException.BizErrEnum.PARAM_ERR.code, message);
	}
	
	public static BizException getEnumException(BizException.BizErrEnum err) {
		return new BizException(err);
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
	
	//---------------------------------------------------------
	
	public static enum BizErrEnum{
		COMM_ERR("1", "操作失败"),
		
		PARAM_ERR("100", "参数错误"), 
		ENCRYPT_ERR("101", "加密错误"), 
		TIMEOUT_ERR("102", "业务处理超时"), 
		DATA_ERR("103", "数据错误"), 
		AUTH_ERR("104", "用户未登录"),
		OPT_ERR("105", "调用错误"),
		
		;
		
		private String code;
		private String message;
		
		private BizErrEnum(String code, String message) {
			this.code = code;
			this.message = message;
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
}
