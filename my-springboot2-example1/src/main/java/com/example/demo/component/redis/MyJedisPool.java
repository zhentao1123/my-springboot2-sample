package com.example.demo.component.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * JedisPool子类，构造器不带database
 * 获取Jedis对象的方法自定database
 * @author bob
 *
 */
public class MyJedisPool extends JedisPool{

	public MyJedisPool(final GenericObjectPoolConfig poolConfig, 
			final String host, int port, int timeout, final String password) {
		//redis分布式后需要该换构造函数
		super(poolConfig, host, port, timeout, password);
	}
	
	public Jedis getResource(Integer database) {
		Jedis jedis = super.getResource();
		jedis.select(database);
		return jedis;
	}
	
	@Override
	@Deprecated
	public Jedis getResource() {
		throw new IllegalArgumentException();
	}
}
