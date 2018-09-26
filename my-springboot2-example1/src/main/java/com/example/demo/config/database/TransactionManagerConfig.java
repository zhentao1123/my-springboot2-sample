package com.example.demo.config.database;

import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages={"com.example.demo.dao"}, entityManagerFactoryRef="entityManagerFactory", transactionManagerRef="transactionManager")
//@EntityScan(basePackages={"com.example.demo.dao.entity"})
public class TransactionManagerConfig {

	public static final String PACKAGE_TO_SCAN = "com.example.demo.dao";
	
	@Resource(name = "dataSourceHikariCP")
	DataSource dataSource;
	
	@Resource(name = "entityManagerFactory")
	EntityManagerFactory entityManagerFactory;
	
	@Resource(name = "transactionManager")
	JpaTransactionManager jpaTransactionManager;
	
	// -- TransactionTemplate 编程式事务使用 --
	@Bean(name = "transactionTemplate")
	public TransactionTemplate transactionTemplate() {
		TransactionTemplate transactionTemplate = new TransactionTemplate();
		transactionTemplate.setTransactionManager(jpaTransactionManager);
		return transactionTemplate;
	}
	
	// -- TransactionManager --
	@Bean(name = "transactionManager")
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setDataSource(dataSource);
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	// -- EntityManageFactory --
	@Bean(name = "entityManagerFactory")
	public EntityManagerFactory entityManagerFactory() 
	{
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setPackagesToScan(PACKAGE_TO_SCAN);
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLInnoDBDialect");
		jpaVendorAdapter.setShowSql(true);
		jpaVendorAdapter.setGenerateDdl(false);
		factory.setJpaVendorAdapter(jpaVendorAdapter);
		factory.setJpaProperties(hibernateProperties()); //似乎和HibernateJpaVendorAdapter有重复，为了保准设置生效暂且都设置
		factory.afterPropertiesSet(); //在完成了其它所有相关的配置加载以及属性设置后,才初始化
		return factory.getObject();
	}
	
	private Properties hibernateProperties() {  
        Properties properties = new Properties();  
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
        properties.put("hibernate.show.sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "none");
        return properties;  
    }  

	// --JdbcTemplate--
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }
    
    // --NamedParameterJdbcTemplate--
    @Bean(name = "namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }
	
	// -- DataSource --
}
