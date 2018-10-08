package com.example.demo.web.request;

import com.example.demo.web.request.to.FindTestByNameVO;

import lombok.Data;

@Data
public class FindTestByNameRequest extends BaseRequest{
	private FindTestByNameVO data;
}
