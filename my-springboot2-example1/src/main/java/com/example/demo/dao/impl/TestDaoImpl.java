package com.example.demo.dao.impl;

import org.springframework.stereotype.Repository;

import com.example.demo.dao.TestDao;
import com.example.demo.dao.entity.Test;

@Repository
public class TestDaoImpl extends BaseJdbcDao implements TestDao{

	@Override
	public Long saveTest(Test test) throws Exception {
		/*
		String sql = "INSERT INTO test (`name`, age)VALUES(?, ?)";
		return super.addObjectReturnLongId(sql, test.getName(), test.getAge());
		*/
		
		String sql = "INSERT INTO test (`name`, age)VALUES(:name, :age)";
		return super.addObjectReturnLongId(sql, test);
	}

}
