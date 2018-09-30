package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.TestDao;
import com.example.demo.dao.entity.Test;
import com.example.demo.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	TestDao testDao;
	
	@Override
	@Transactional(readOnly=false, rollbackFor=Exception.class)
	public Test addTest(Test test) throws Exception {
		return testDao.saveTest(test);
	}

}
