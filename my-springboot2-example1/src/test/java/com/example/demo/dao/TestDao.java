package com.example.demo.dao;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.ApplicationTests;
import com.example.demo.dao.repository.TestRepository;
import com.example.demo.util.json.JsonUtil;

public class TestDao extends ApplicationTests{
	
	private static final Logger log = LoggerFactory.getLogger(TestDao.class);
	
	@Autowired
	TestRepository testRepository;
	
	@Test
	public void test1() {
		List<com.example.demo.dao.entity.Test> list = testRepository.findAll();
		String json = JsonUtil.obj2json(list);
		log.info(json);
		System.out.println(json);
	}
	
}
