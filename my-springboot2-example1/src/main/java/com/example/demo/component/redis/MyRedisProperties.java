package com.example.demo.component.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyRedisProperties {
	
	@Value("${redis.timeout}")
	private int timeout;
	
	@Value("${redis.host}")
	private String host;
	
	@Value("${redis.port}")
	private int port;
	
	@Value("${redis.password}")
	private String password;
	
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
