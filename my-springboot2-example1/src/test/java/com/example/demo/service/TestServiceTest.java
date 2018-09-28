package com.example.demo.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.example.demo.ApplicationTests;

public class TestServiceTest extends ApplicationTests{

	private static final Logger log = LoggerFactory.getLogger(TestServiceTest.class);
	
	@Autowired
	TestService testService;
	
	@Test
	@Rollback(false)
	public void test1() throws Exception {
		com.example.demo.dao.entity.Test test = new com.example.demo.dao.entity.Test();
		test.setName("Miki");
		test.setAge(2);
		Long id = testService.addTest(test);
		System.out.println(id);
	}
}
