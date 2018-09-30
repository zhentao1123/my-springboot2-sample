package com.example.demo.config.mail;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
	
	@Bean(name="logMailSender")
	@ConfigurationProperties(prefix="mail.log.sender")
	public JavaMailSender logMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty("mail.smtp.auth", "true");
		javaMailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		javaMailSender.setJavaMailProperties(javaMailProperties);
		return javaMailSender;
	}
	
}
