package me.alanx.ecomer.web.admin;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class EComerAdminApplicationListener implements ApplicationListener<ContextRefreshedEvent>{

	
	private static final Logger log = LoggerFactory.getLogger(EComerAdminApplicationListener.class);

	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		String[] activeProfiles = event.getApplicationContext().getEnvironment().getActiveProfiles();
		log.info("{} active profiles: {}", activeProfiles.length, Arrays.toString(activeProfiles));
	}

}
