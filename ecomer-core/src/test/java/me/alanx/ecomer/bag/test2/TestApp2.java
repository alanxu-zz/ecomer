package me.alanx.ecomer.bag.test2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import me.alanx.ecomer.test.MockConfig;

public class TestApp2 {
	

	

	


	public static void main(String[] args) throws InterruptedException {
		
			System.setProperty("spring.profiles.active", "test,init");
			
			ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class, MockConfig.class);
	
			Test t = ctx.getBean(Test.class);
			t.doTest();
	
	}
}