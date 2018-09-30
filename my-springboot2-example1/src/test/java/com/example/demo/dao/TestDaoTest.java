package com.example.demo.dao;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.example.demo.ApplicationTests;
import com.example.demo.dao.repository.TestRepository;
import com.example.demo.util.json.JsonUtil;

public class TestDaoTest extends ApplicationTests{
	
	private static final Logger log = LoggerFactory.getLogger(TestDaoTest.class);
	
	@Autowired
	TestRepository testRepository;
	
	@Autowired
	TestDao testDao;
	
	@Test
	public void test1() {
		List<com.example.demo.dao.entity.Test> list = testRepository.findAll();
		String json = JsonUtil.obj2json(list);
		log.info(json);
	}
	
	@Test
	public void testCacheRead() {
		try {
			com.example.demo.dao.entity.Test test = testDao.findTestByName("john");
			log.info(JsonUtil.obj2json(test));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Rollback(false)
	public void testCacheAdd() {
		try {
			com.example.demo.dao.entity.Test test = new com.example.demo.dao.entity.Test();
			test.setName("miki");
			test.setAge(20);
			test = testDao.saveTest(test);
			log.info(test.getId() + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
