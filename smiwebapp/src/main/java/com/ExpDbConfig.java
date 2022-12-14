package com;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  entityManagerFactoryRef = "expEntityManagerFactory",
  transactionManagerRef = "expTransactionManager",
  basePackages = { "com.expandable.repository" }
)
public class ExpDbConfig {
	
	  @Bean(name = "expDataSource")
	  @ConfigurationProperties(prefix = "exp.datasource")
	  public DataSource dataSource() {
	    return DataSourceBuilder.create().build();
	  }
	  
	  @Bean(name = "expEntityManagerFactory")
	  public LocalContainerEntityManagerFactoryBean 
	  barEntityManagerFactory(
	    EntityManagerFactoryBuilder builder,
	    @Qualifier("expDataSource") DataSource dataSource
	  ) {
	    return
	      builder
	        .dataSource(dataSource)
	        .packages("com.expandable.model")
	        .persistenceUnit("com.expandable")
	        .build();
	  }
	  @Bean(name = "expTransactionManager")
	  public PlatformTransactionManager expTransactionManager(
	    @Qualifier("expEntityManagerFactory") EntityManagerFactory
	    expEntityManagerFactory
	  ) {
	    return new JpaTransactionManager(expEntityManagerFactory);
	  }

}
