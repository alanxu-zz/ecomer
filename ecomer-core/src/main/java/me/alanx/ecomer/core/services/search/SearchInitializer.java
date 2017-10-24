package me.alanx.ecomer.core.services.search;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

import me.alanx.ecomer.core.services.search.SearchService;

public class SearchInitializer implements ApplicationListener<ContextStartedEvent> {

	@Override
	public void onApplicationEvent(ContextStartedEvent event) {
		 ApplicationContext applicationContext = event.getApplicationContext();
		 /** init search service **/
		 /*SearchService searchService = (SearchService)applicationContext.getBean("productSearchService");
		 searchService.initService();*/
		
	}

}
