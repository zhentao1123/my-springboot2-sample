package com.example.demo.config.database;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {
		
	@Bean(name="dataSourceHikariCP")
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public DataSource dataSourceHikariCP() {
		HikariDataSource dataSource = new HikariDataSource();
		return dataSource;
	}
}
