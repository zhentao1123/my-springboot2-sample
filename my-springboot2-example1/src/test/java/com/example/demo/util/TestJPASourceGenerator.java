package com.example.demo.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.example.demo.generator.jpa.JPASourceGenerator;
import com.example.demo.generator.jpa.MysqlInfoUtil;

public class TestJPASourceGenerator {
	@Test
	public void generatorSource() throws IOException, SQLException {

		String url = "jdbc:mysql://127.0.0.1:3306/db1?useSSL=false&autoCommit=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&maxReconnects=2&useCompression=true"; 
		String userName = "root"; 
		String password = "root"; 
		String database = "db1"; 
		
		JPASourceGenerator sourceGenerator = new JPASourceGenerator(
				url, userName, password, database, 
				"/flt/java", "com.example.demo");
		
		String[] singleTable = new String[] {"test"};
		if(singleTable.length==0) {
			MysqlInfoUtil mysqlInfoUtil = new MysqlInfoUtil(url, userName, password, database);
			List<String> tableNames = mysqlInfoUtil.getTableNames();
			for(String tableName : tableNames) {
				sourceGenerator.generateEntity("entity.flt", "dao.entity", tableName, true, "dao.entity", "BaseEntity");
				sourceGenerator.generateRepository("repository.flt", "dao.repository", "dao.entity", tableName, false);
			}
		}else {
			for(String tableName : singleTable) {
				sourceGenerator.generateEntity("entity.flt", "dao.entity", tableName, true, "dao.entity", "BaseEntity");
				sourceGenerator.generateRepository("repository.flt", "dao.repository", "dao.entity", tableName, false);
			}
		}
	}
}
