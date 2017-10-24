package me.alanx.ecomer.web.listeners;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.session.HttpSessionCreatedEvent;

public class LoginSuccessListener implements ApplicationListener<ApplicationEvent>{

	
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		SecurityContextHolder.getContext();
		if (event instanceof HttpSessionCreatedEvent) {
			
		} else if (event instanceof AuthenticationSuccessEvent) {
			
		}
		
	}
	
	

}
