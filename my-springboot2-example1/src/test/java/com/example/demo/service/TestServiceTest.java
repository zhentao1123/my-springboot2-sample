package com.example.demo.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.example.demo.ApplicationTests;
import com.example.demo.util.json.JsonUtil;
import com.example.demo.web.request.to.SaveTestVO;
import com.example.demo.web.request.to.TestVO;

public class TestServiceTest extends ApplicationTests{

	private static final Logger log = LoggerFactory.getLogger(TestServiceTest.class);
	
	@Autowired
	TestService testService;
	
	@Test
	@Rollback(false)
	public void test1() throws Exception {
		SaveTestVO test = new SaveTestVO();
		test.setName("Miki");
		test.setAge(2);
		TestVO result = testService.saveTest(test);
		log.info(JsonUtil.obj2json(result));
	}
}
