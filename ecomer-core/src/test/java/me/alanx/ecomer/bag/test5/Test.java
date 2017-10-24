package me.alanx.ecomer.bag.test5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class Test {
	
	private final Logger log = LoggerFactory.getLogger(Test.class);

	public Test() {}
	
	@Autowired
	ParentRepository pRepo;
	
	@Transactional
	public void doTest() {
		
		Parent p = new Parent();
		//p.setName("a");
		
		pRepo.saveAndFlush(p);
		
		Child c1 = new Child();
		Child c2 = new Child();
		Child c3 = new Child();
		Child c4 = new Child();
		
		c1.setName("a");
		c2.setName("b");
		c3.setName("c");
		c4.setName("d");
		
		p.getChildren().add(c1);
		p.getChildren().add(c2);
		p.getChildren().add(c3);
		p.getChildren().add(c4);
		
		pRepo.saveAndFlush(p);
		
		for(Child c : p.getChildren()) {
			log.info("{}]'s id: {}", c.getName(), c.getId());
		}
		
		
		
	}
	
}