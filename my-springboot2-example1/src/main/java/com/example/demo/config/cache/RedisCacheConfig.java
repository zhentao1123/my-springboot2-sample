package com.example.demo.config.cache;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.DefaultJedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.example.demo.config.redis.RedisJsonSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport{

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
    
    @Value("${redis.db-index.cache}")
    private Integer cacheIndexCache;
    
	@Bean(name="cacheRedisConnectionFactory")
	JedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration();
		standaloneConfig.setHostName(host);
		standaloneConfig.setPassword(RedisPassword.of(password));
		standaloneConfig.setPort(port);
		standaloneConfig.setDatabase(cacheIndexCache);
		
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(maxActive);
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMinIdle(minIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWait);
		jedisPoolConfig.setTestOnCreate(true);
		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setTestOnReturn(true);
		
		JedisClientConfiguration clientConfig = JedisClientConfiguration
													.builder()
													.usePooling().poolConfig(jedisPoolConfig)
													.build();
		
		//该处可以改为哨兵模式或集群模式构造函数
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(standaloneConfig, clientConfig);
		return jedisConnectionFactory;
	}
	
	@Bean
	@Qualifier(value="cacheRedisConnectionFactory")
	public CacheManager cacheManager(RedisConnectionFactory factory) {
		// 生成一个默认配置，通过config对象即可对缓存进行自定义配置
	    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
	    			.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new RedisJsonSerializer<>(Object.class)))  //自定义序列化
	    			.entryTtl(Duration.ofMinutes(1))	// 设置缓存的默认过期时间，也是使用Duration设置
	            	.disableCachingNullValues();		// 不缓存空值

	    // 设置一个初始化的缓存空间set集合
	    Set<String> cacheNames = new HashSet<>();
	    cacheNames.add("test-cache");
	    cacheNames.add("my-redis-cache2");

	    // 对每个缓存空间应用不同的配置
	    Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
	    configMap.put("test-cache", config.entryTtl(Duration.ofMinutes(5)));
	    configMap.put("my-redis-cache2", config.entryTtl(Duration.ofSeconds(120)));

	    // 使用自定义的缓存配置初始化一个cacheManager
	    RedisCacheManager cacheManager = RedisCacheManager.builder(factory)     
	            .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
	            .withInitialCacheConfigurations(configMap)
	            .build();
	    return cacheManager;
	}

	@Bean
	@Qualifier(value="cacheRedisConnectionFactory")
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);         
		
		//使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
		Jackson2JsonRedisSerializer<?> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		serializer.setObjectMapper(mapper);
		template.setValueSerializer(serializer);
		
		//使用StringRedisSerializer来序列化和反序列化redis的key值
		template.setKeySerializer(new StringRedisSerializer());
		
		template.afterPropertiesSet();
		return template;
	}

	/*
	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for(Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}
	*/
}
