package me.alanx.ecomer.bag.test7;

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

	public Test() {
	}

	@PersistenceUnit
	EntityManagerFactory emf;

	// @Autowired
	// PlatformTransactionManager tm;

	@Autowired
	CountryRepository contryRepo;

	public void createCountries() throws ServiceException {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		log.info(String.format("%s : Populating Countries ", ""), "");

		Country2 country = new Country2();
		contryRepo.save(country);

		CountryDescription2 description = new CountryDescription2();
		CountryDescription2 description2 = new CountryDescription2();
		CountryDescription2 description3 = new CountryDescription2();
		CountryDescription2 description4 = new CountryDescription2();
		CountryDescription2 description5 = new CountryDescription2();

		country.getDescriptions().add(description);
		country.getDescriptions().add(description2);
		country.getDescriptions().add(description3);
		country.getDescriptions().add(description4);
		country.getDescriptions().add(description5);
		// description.setCountry(country);
		contryRepo.save(country);

		for (CountryDescription2 cd : country.getDescriptions()) {
			log.debug("---- descp id: {}, country id: {}",
					cd.getId()/* , cd.getCountry().getId() */);
		}

		em.getTransaction().commit();
	}

}