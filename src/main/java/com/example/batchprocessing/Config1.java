package com.example.batchprocessing;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class Config1 {



	


	 @Bean(name = "dataSource")
	 @BatchDataSource
//	 @ConfigurationProperties("spring.metadate-datasource")
	 public DataSource H2Datasource() {
	   EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
	   EmbeddedDatabase embeddedDatabase = builder
			   .setName("metadb")
//	   .addScript("classpath:org/springframework/batch/core/schema-drop h2.sql")
//	   .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
	   .setType(EmbeddedDatabaseType.H2)
	   .build();
	   return embeddedDatabase;
	 }
	 
	 @Bean
	 @Primary
	 @ConfigurationProperties("spring.datasource")
	 public DataSourceProperties oracleDatasourceProperties() {
	   return new DataSourceProperties();
	 }
	 
	 
	 @Bean
	 @Primary
	 @ConfigurationProperties("spring.datasource.hikari")
	 public DataSource oracleDatasource() {
	   return oracleDatasourceProperties().initializeDataSourceBuilder()
	   .type(HikariDataSource.class).build();
	 }
	 
	 
}
