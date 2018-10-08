package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.TestDao;
import com.example.demo.dao.entity.Test;
import com.example.demo.service.TestService;
import com.example.demo.util.reflect.BeanMapper;
import com.example.demo.web.request.to.FindTestByNameVO;
import com.example.demo.web.request.to.RemoveTestByNameVO;
import com.example.demo.web.request.to.SaveTestVO;
import com.example.demo.web.request.to.TestVO;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	TestDao testDao;
	
	@Override
	@Transactional(readOnly=false, rollbackFor=Exception.class)
	public TestVO saveTest(SaveTestVO param) throws Exception {
		Test test = BeanMapper.map(param, Test.class);
		Test result = testDao.saveTest(test);
		return BeanMapper.map(result, TestVO.class);
	}

	@Override
	public TestVO findTestByName(FindTestByNameVO param) throws Exception {
		Test test = testDao.findTestByName(param.getName());
		TestVO result = BeanMapper.map(test, TestVO.class);
		return result;
	}

	@Override
	@Transactional(readOnly=false, rollbackFor=Exception.class)
	public boolean removeTestByName(RemoveTestByNameVO param) throws Exception {
		return testDao.removeTestByName(param.getName());
	}

}
