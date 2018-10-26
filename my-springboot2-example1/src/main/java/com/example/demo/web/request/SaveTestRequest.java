package com.example.demo.web.request;

import com.example.demo.web.request.to.SaveTestVO;

public class SaveTestRequest extends BaseRequest{
	private SaveTestVO data;

	public SaveTestVO getData() {
		return data;
	}

	public void setData(SaveTestVO data) {
		this.data = data;
	}
	
}
