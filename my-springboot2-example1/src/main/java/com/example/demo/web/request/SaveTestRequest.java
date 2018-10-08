package com.example.demo.web.request;

import com.example.demo.web.request.to.SaveTestVO;

import lombok.Data;

@Data
public class SaveTestRequest extends BaseRequest{
	private SaveTestVO data;
}
