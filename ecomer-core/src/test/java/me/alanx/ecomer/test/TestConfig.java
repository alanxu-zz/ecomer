package me.alanx.ecomer.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Profile("test")
@Configuration
@ImportResource("test-app.xml")
@PropertySource("classpath:test.properties")
@ComponentScan({"me.alanx.ecomer.core.services",
				"me.alanx.ecomer.core.utils",
				"me.alanx.ecomer.core.cms",
				"me.alanx.ecomer.core.auth", 
				"me.alanx.ecomer.integration.modules", 
				"me.alanx.ecomer.cache", 
				"me.alanx.ecomer.test"})
public class TestConfig {

	// This is to get the placeholder use the properties configured via @PropertySource
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
