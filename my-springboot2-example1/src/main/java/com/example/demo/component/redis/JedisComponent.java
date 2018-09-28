package com.example.demo.component.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisComponent {

//	@Autowired
//    private JedisPool jedisPool;
	
	@Autowired
	private MyJedisPool jedisPool;
	
	public <D> D process(Integer database, JedisProcessor<D> jedisProcessor) throws Exception{
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource(database);
			return jedisProcessor.doProcess(jedis);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(null!=jedis) {
					jedis.close();
				}
			} catch (Exception e) {}
		}
	}
	
	public static interface JedisProcessor<D>{
		public abstract D doProcess(Jedis jedis) throws Exception;
	}

}
