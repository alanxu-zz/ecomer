package me.alanx.ecomer.bag.test1;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;


@Component
public class Test {
	
	private final Logger log = LoggerFactory.getLogger(Test.class);

	public Test() {}
	
	@PersistenceUnit
	EntityManagerFactory emf;
	
	//@Autowired
	//PlatformTransactionManager tm;
	
	@Transactional
	public void doTest() {
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("SomeTxName");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NEVER);
		
		//TransactionStatus status = tm.getTransaction(def);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Parent p = new Parent();
		p.setName("a");
		
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
		
		em.persist(p);
		
		for(Child c : p.getChildren()) {
			log.info("{}]'s id: {}", c.getName(), c.getId());
		}
		
		em.getTransaction().commit();
		
		//tm.commit(status);
		
	}
	
}