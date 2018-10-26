package com.example.demo.web.request;

import com.example.demo.web.request.to.RemoveTestByNameVO;

public class RemoveTestByNameRequest extends BaseRequest{
	private RemoveTestByNameVO data;

	public RemoveTestByNameVO getData() {
		return data;
	}

	public void setData(RemoveTestByNameVO data) {
		this.data = data;
	}
	
}
