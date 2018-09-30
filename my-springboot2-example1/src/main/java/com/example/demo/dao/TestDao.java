package com.example.demo.dao;

import java.util.List;

import com.example.demo.dao.entity.Test;

public interface TestDao {
	
	public Long saveTest(Test test) throws Exception;
	
	public Test findTestByName(String name) throws Exception;
	
	public boolean removeTestByName(String name) throws Exception;
}
