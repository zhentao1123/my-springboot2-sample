package com.example.demo.web.request;

import com.example.demo.web.request.to.RemoveTestByNameVO;

import lombok.Data;

@Data
public class RemoveTestByNameRequest extends BaseRequest{
	private RemoveTestByNameVO data;
}
