package com.example.demo.web.request;

import com.example.demo.web.request.to.FindTestByNameVO;

public class FindTestByNameRequest extends BaseRequest{
	private FindTestByNameVO data;

	public FindTestByNameVO getData() {
		return data;
	}

	public void setData(FindTestByNameVO data) {
		this.data = data;
	}
	
}
