package com.example.demo.service;

import com.example.demo.web.request.to.FindTestByNameVO;
import com.example.demo.web.request.to.RemoveTestByNameVO;
import com.example.demo.web.request.to.SaveTestVO;
import com.example.demo.web.request.to.TestVO;

public interface TestService {
	
	public TestVO saveTest(SaveTestVO param) throws Exception;
	
	public TestVO findTestByName(FindTestByNameVO param) throws Exception;
	
	public boolean removeTestByName(RemoveTestByNameVO param) throws Exception;
}
