package com.example.demo;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	@PostConstruct
	public void logSomething() {
//		logger.debug("Sample Debug Message");
//		logger.trace("Sample Trace Message");
//		logger.info("Sample Info Message");
}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
