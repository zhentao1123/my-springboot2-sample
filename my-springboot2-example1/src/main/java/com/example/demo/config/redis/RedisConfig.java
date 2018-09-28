package com.example.demo.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.example.demo.component.redis.MyJedisPool;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
	
	@Value("${redis.host}")
    private String host;
    
    @Value("${redis.port}")
    private Integer port;
    
    @Value("${redis.password}")
    private String password;
    
    @Value("${redis.timeout}")
    private Integer timeout;
    
    @Value("${redis.pool.max-wait}")
    private Long maxWait;
    
    @Value("${redis.pool.max-active}")
    private Integer maxActive;
    
    @Value("${redis.pool.max-idle}")
    private Integer maxIdle;
    
    @Value("${redis.pool.min-idle}")
    private Integer minIdle;
    
    @Bean
    @Lazy
	JedisPoolConfig jedisPoolConfig(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(maxActive);
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMinIdle(minIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWait);
		
		jedisPoolConfig.setTestOnCreate(true);
		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setTestOnReturn(true);
		return jedisPoolConfig;
	}
    
    
    //== redis data 需要 ======================
    @Bean
	@Lazy
	JedisConnectionFactory redisConnectionFactory() {
    	//redis分布式后需要该换构造，加入哨兵配置
		return new JedisConnectionFactory(jedisPoolConfig());
	}
	
    @Bean
	MyJedisPool myJedisPool() {
		return new MyJedisPool(jedisPoolConfig(), host, port, timeout, password);
	}
    
    /*
	@Bean
	@Lazy
	JedisPool jedisPool() {
		return new JedisPool(jedisPoolConfig(), host, port, timeout, password, database);
	}
	*/
}
