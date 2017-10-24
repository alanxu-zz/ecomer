package me.alanx.ecomer.bag.test3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import me.alanx.ecomer.core.exception.ServiceException;

public class TestApp3 {
	

	

	


	public static void main(String[] args) throws InterruptedException {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

		Test t = ctx.getBean(Test.class);
		try {
			t.createCountries();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread.sleep(Long.MAX_VALUE);
		
	}

}
