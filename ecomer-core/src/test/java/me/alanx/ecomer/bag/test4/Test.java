package me.alanx.ecomer.bag.test4;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	
	@Autowired
	CountryRepository contryRepo;
	
	@Autowired
	LanguageRepository langRepo;
	
	public void createCountries() throws ServiceException {
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		log.info(String.format("%s : Populating Countries ", ""), "");
		List<Language> languages = new ArrayList<>();
		languages.add(new Language("en"));
		languages.add(new Language("fr"));
		for (Language l : languages) {
			langRepo.save(l);
		}
		for(String code : SchemaConstant.COUNTRY_ISO_CODE) {
			Locale locale = SchemaConstant.LOCALES.get(code);
			if (locale != null) {
				Country country = new Country(code);
				contryRepo.save(country);
				
				for (Language language : languages) {
					String name = locale.getDisplayCountry(new Locale(language.getCode()));
					CountryDescription description = new CountryDescription(language, name);
					log.debug("---- {} - {}", language.getId(), country.getId());
					
					country.getDescriptions().add(description);
					description.setCountry(country);
					contryRepo.save(country);
					
					for(CountryDescription cd : country.getDescriptions()) {
						log.debug("---- descp id: {}, country id: {}", cd.getId(), cd.getCountry().getId());
					}
					
				}
			}
		}
		
		em.getTransaction().commit();
	}
	
}