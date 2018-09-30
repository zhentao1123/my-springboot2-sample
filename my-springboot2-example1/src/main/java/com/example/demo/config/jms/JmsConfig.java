package com.example.demo.config.jms;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@Configuration
public class JmsConfig {

	@Bean(name="test-queue")
	public Queue testQueue() {
		return new ActiveMQQueue("test-queue");
	}
}
