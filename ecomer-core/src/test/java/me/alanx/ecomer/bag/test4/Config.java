package me.alanx.ecomer.bag.test4;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource("classpath:test.properties")
@ComponentScan(basePackageClasses = {Test.class})
@EnableJpaRepositories({"me.alanx.ecomer.test.bag.test4"})
public class Config {

	
	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public DataSource dataSource(
			@Value("${db.driver_class}") String driverClass,
			@Value("${db.url}") String url,
			@Value("${db.username}") String username,
			@Value("${db.password}") String password
			) {
		DriverManagerDataSource ds = new DriverManagerDataSource(url, username, password);
		ds.setDriverClassName(driverClass);
		return ds;
	}
	
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
    		DataSource dataSource, 
    		JpaVendorAdapter jpaVendorAdapter,
    		@Qualifier("props")Properties props) throws IOException {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setPackagesToScan("me.alanx.ecomer.core.model");
        //lef.setPackagesToScan("me.alanx.ecomer.test.bag");
        
        Map<String, String> jpaPropsMap = new HashMap<>();
        jpaPropsMap.put("hibernate.default_schema", props.getProperty("db.schema"));
        jpaPropsMap.put("hibernate.hbm2ddl.auto", props.getProperty("hibernate.hbm2ddl.auto"));
        jpaPropsMap.put("hibernate.show_sql", props.getProperty("true"));
        jpaPropsMap.put("hibernate.cache.provider_class", props.getProperty("org.hibernate.cache.EhCacheProvider"));
        jpaPropsMap.put("hibernate.cache.use_second_level_cache", props.getProperty("false"));
        jpaPropsMap.put("hibernate.physical_naming_strategy", props.getProperty("hibernate.physical_naming_strategy"));
        lef.setJpaPropertyMap(jpaPropsMap);
        
        return lef;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
        return hibernateJpaVendorAdapter;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    	JpaTransactionManager transactionManager = new JpaTransactionManager();
    	transactionManager.setEntityManagerFactory(emf);
    	transactionManager.setNestedTransactionAllowed(false);
    	return transactionManager;
    }
    
    @Bean
    Properties props() throws IOException {
    	PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
    	propertiesFactoryBean.setLocation(new ClassPathResource("test.properties"));
    	propertiesFactoryBean.afterPropertiesSet();
    	return propertiesFactoryBean.getObject();
    }
	
}