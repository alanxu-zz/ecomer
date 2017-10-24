package me.alanx.ecomer.bag.test3;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

import me.alanx.ecomer.core.constants.SchemaConstant;
import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.reference.Country;
import me.alanx.ecomer.core.model.reference.CountryDescription;
import me.alanx.ecomer.core.model.reference.Language;


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
		
		em.persist(p);
		
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
	
	public void createCountries() throws ServiceException {
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		log.info(String.format("%s : Populating Countries ", ""), "");
		List<Language> languages = new ArrayList<>();
		languages.add(new Language("en"));
		languages.add(new Language("fr"));
		for (Language l : languages) {
			em.persist(l);
		}
		for(String code : SchemaConstant.COUNTRY_ISO_CODE) {
			Locale locale = SchemaConstant.LOCALES.get(code);
			if (locale != null) {
				Country country = new Country(code);
				em.persist(country);
				
				for (Language language : languages) {
					String name = locale.getDisplayCountry(new Locale(language.getCode()));
					CountryDescription description = new CountryDescription(language, name);
					log.debug("---- {} - {}", language.getId(), country.getId());
					
					country.getDescriptions().add(description);
					description.setCountry(country);
					em.persist(country);
					
					for(CountryDescription cd : country.getDescriptions()) {
						log.debug("---- descp id: {}, country id: {}", cd.getId(), cd.getCountry().getId());
					}
					
				}
			}
		}
		
		em.getTransaction().commit();
	}
	
}