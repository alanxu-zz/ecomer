package me.alanx.ecomer.bag.test5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestApp5 {
	

	
	static ParentRepository pRepo;

	


	public static void main(String[] args) throws InterruptedException {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

		Test t = ctx.getBean(Test.class);
		t.doTest();

		
		
		
		
		Thread.sleep(Long.MAX_VALUE);
		
	}

}
