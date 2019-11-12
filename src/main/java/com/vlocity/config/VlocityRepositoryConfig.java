package com.vlocity.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "primaryEntityManagerFactory", 
        transactionManagerRef = "primaryTransactionManager",
        basePackages = { "com.vlocity.repository" })
public class VlocityRepositoryConfig {

	@Autowired
    private Environment env;
	
	
	@Primary
	@Bean
	public DataSource primaryDataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
 
        return dataSource;
	}
	
	@Primary
	@Bean
	public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(primaryDataSource());
        em.setPackagesToScan(new String[] { "com.vlocity.domain" });
 
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(Boolean.valueOf(env.getProperty("spring.jpa.generate-ddl")));
        vendorAdapter.setShowSql(Boolean.valueOf(env.getProperty("spring.jpa.show-sql")));
        
        em.setJpaVendorAdapter(vendorAdapter);
        
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.hibernate.dialect"));
        properties.put("hibernate.naming.physical-strategy", env.getProperty("spring.jpa.hibernate.naming.physical-strategy"));
        
        em.setJpaPropertyMap(properties);
 
        return em;
	}

	@Primary
    @Bean
    public PlatformTransactionManager primaryTransactionManager() {
		
		JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(primaryEntityManagerFactory().getObject());
        
        return transactionManager;
    }
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}	
}
