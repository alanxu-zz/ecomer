package me.alanx.ecomer.bag.test1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestApp1 {
	

	

	


	public static void main(String[] args) throws InterruptedException {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

		Test t = ctx.getBean(Test.class);
		t.doTest();
		
		Thread.sleep(Long.MAX_VALUE);
		
	}

}
