package com.example.demo.dao.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.TestDao;
import com.example.demo.dao.entity.Test;

@Repository
public class TestDaoImpl extends BaseJdbcDao implements TestDao{

	/**
	 * @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存
	 */
    @CachePut(value = "test-cache", key = "#test.name", unless = "#test=null")
	@Override
	public Long saveTest(Test test) throws Exception {
		/*
		String sql = "INSERT INTO test (`name`, age)VALUES(?, ?)";
		return super.addObjectReturnLongId(sql, test.getName(), test.getAge());
		*/
		
		String sql = "INSERT INTO test (`name`, age)VALUES(:name, :age)";
		return super.addObjectReturnLongId(sql, test);
	}
	
	/**
	 * @Cacheable 应用到读取数据的方法上，先从缓存中读取，如果没有再从DB获取数据，然后把数据添加到缓存中，unless 表示条件表达式成立的话不放入缓存
	 */
    //@Cacheable(value = "test-cache")
    @Cacheable(value = "test-cache", key = "#name", unless = "#result=null")
    //@Cacheable(value="test-cache",key="#root.targetClass + #root.methodName + #name", unless = "#result=null")
    //@Cacheable(value = "test-cache",key ="T(String).valueOf(#root.targetClass).concat('-').concat(#root.methodName).concat('-').concat(#name)", unless = "#result=null")
    @Override
	public Test findTestByName(String name) throws Exception{
		String sql = "SELECT * FROM test WHERE name = ? LIMIT 1";
		return super.queryObject(sql, Test.class, name);
	}

    /**
     * @CacheEvict 应用到删除数据的方法上，调用方法时会从缓存中删除对应key的数据
     */
    @CacheEvict(value = "test-cache", key = "#name", condition = "#result=true")
    public boolean removeTestByName(String name) throws Exception{
    	String sql = "DELETE FROM test WHERE name = ?";
    	return super.update(sql, name)>0;
    }
}
