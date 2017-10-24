package me.alanx.ecomer.web.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class EComerAdminApplication {
	
	private static final Logger log = LoggerFactory.getLogger(EComerAdminApplication.class);

	public static void main(String[] args) throws Exception {
		
		SpringApplication.run(EComerAdminApplication.class, args);
		
		
	
	}
	
	@Bean
	public String test(Environment env) {
		log.debug("================ ecomer.authorization.json: {} ==============", env.getProperty("ecomer.authorization.json"));
		return env.getProperty("ecomer.authorization.json");
	}
	



}
