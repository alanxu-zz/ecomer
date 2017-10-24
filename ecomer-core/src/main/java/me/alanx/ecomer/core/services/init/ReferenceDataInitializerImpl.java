package me.alanx.ecomer.core.services.init;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.alanx.ecomer.core.constants.SchemaConstant;
import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.auth.SecurityQuestion;
import me.alanx.ecomer.core.model.catalog.product.ProductType;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Country;
import me.alanx.ecomer.core.model.reference.CountryDescription;
import me.alanx.ecomer.core.model.reference.Currency;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.reference.Zone;
import me.alanx.ecomer.core.model.reference.ZoneDescription;
import me.alanx.ecomer.core.model.system.IntegrationModule;
import me.alanx.ecomer.core.model.tax.taxclass.TaxClass;
import me.alanx.ecomer.core.repositories.security.SecurityQuestionRepository;
import me.alanx.ecomer.core.services.catalog.product.type.ProductTypeService;
import me.alanx.ecomer.core.services.merchant.MerchantStoreService;
import me.alanx.ecomer.core.services.reference.country.CountryService;
import me.alanx.ecomer.core.services.reference.currency.CurrencyService;
import me.alanx.ecomer.core.services.reference.init.ReferenceDataInitializer;
import me.alanx.ecomer.core.services.reference.language.LanguageService;
import me.alanx.ecomer.core.services.reference.loader.IntegrationModulesLoader;
import me.alanx.ecomer.core.services.reference.loader.ZonesLoader;
import me.alanx.ecomer.core.services.reference.zone.ZoneService;
import me.alanx.ecomer.core.services.security.SecurityQuestionService;
import me.alanx.ecomer.core.services.system.ModuleConfigurationService;
import me.alanx.ecomer.core.services.tax.TaxClassService;

@Service("initializationDatabase")
public class ReferenceDataInitializerImpl implements ReferenceDataInitializer {
	
	private static final Logger LOG = LoggerFactory.getLogger(ReferenceDataInitializerImpl.class);
	

	
	@Inject
	private ZoneService zoneService;
	
	@Inject
	private LanguageService languageService;
	
	@Inject
	private CountryService countryService;
	
	@Inject
	private CurrencyService currencyService;
	
	@Inject
	protected MerchantStoreService merchantService;
		
	@Inject
	protected ProductTypeService productTypeService;
	
	@Inject
	private TaxClassService taxClassService;
	
	@Inject
	private ZonesLoader zonesLoader;
	
	@Inject
	private IntegrationModulesLoader modulesLoader;
	
	@Inject
	private ModuleConfigurationService moduleConfigurationService;
	
	@Inject
	SecurityQuestionService securityQuestionService;
	
	private String name;
	
	public boolean isEmpty() {
		return languageService.count() == 0;
	}
	
	@Transactional
	public void populate(String contextName) throws ServiceException {
		this.name =  contextName;
		
		createLanguages();
		createCountries();
		createZones();
		createCurrencies();
		createSubReferences();
		createModules();
		createMerchant();
		createSecurityQuestions();

	}
	


	private void createCurrencies() throws ServiceException {
		LOG.info(String.format("%s : Populating Currencies ", name));

		//Locale [] locales = Locale.getAvailableLocales();
		//Locale l = locales[0];
		
		for (String code : SchemaConstant.CURRENCY_MAP.keySet()) {

		      
            try {
            	java.util.Currency c = java.util.Currency.getInstance(code);
            	
            	if(c==null) {
            		LOG.info(String.format("%s : Populating Currencies : no currency for code : %s", name, code));
            	}
            	
            		//check if it exist
            		
	            	Currency currency = new Currency();
	            	currency.setName(c.getCurrencyCode());
	            	currency.setCurrency(c);
	            	currencyService.create(currency);

            //System.out.println(l.getCountry() + "   " + c.getSymbol() + "  " + c.getSymbol(l));
            } catch (IllegalArgumentException e) {
            	LOG.info(String.format("%s : Populating Currencies : no currency for code : %s", name, code));
            }
        }
	}

	private void createCountries() throws ServiceException {
		LOG.info(String.format("%s : Populating Countries ", name));
		List<Language> languages = languageService.list();
		for(String code : SchemaConstant.COUNTRY_ISO_CODE) {
			Locale locale = SchemaConstant.LOCALES.get(code);
			if (locale != null) {
				Country country = new Country(code);
				country = countryService.create(country);
				
				for (Language language : languages) {
					String name = locale.getDisplayCountry(new Locale(language.getCode()));
					CountryDescription description = new CountryDescription(language, name);
					country = countryService.addCountryDescription(country, description);
				}
			}
		}
	}
	
	private void createZones() throws ServiceException {
		LOG.info(String.format("%s : Populating Zones ", name));
        try {

    		  Map<String,Zone> zonesMap = new HashMap<String,Zone>();
    		  zonesMap = zonesLoader.loadZones("reference/zoneconfig-default.json");
              
              for (Map.Entry<String, Zone> entry : zonesMap.entrySet()) {
            	    String key = entry.getKey();
            	    Zone zone = entry.getValue();
            	    if(zone.getDescriptions()==null) {
            	    	LOG.warn("This zone " + key + " has no descriptions");
            	    	continue;
            	    }
            	    
            	    List<ZoneDescription> zoneDescriptions = zone.getDescriptions();
            	    zone.setDescriptons(null);

            	    zone = zoneService.create(zone);
            	    
            	    for(ZoneDescription description : zoneDescriptions) {
            	    	description.setZone(zone);
            	    	zone = zoneService.addDescription(zone, description);
            	    }
              }

  		} catch (Exception e) {
  		    
  			throw new ServiceException(e);
  		}

	}

	private void createLanguages() throws ServiceException {
		LOG.info(String.format("%s : Populating Languages ", name));
		for(String code : SchemaConstant.LANGUAGE_ISO_CODE) {
			Language language = new Language(code);
			languageService.create(language);
		}
	}

	private void createMerchant() throws ServiceException {
		LOG.info(String.format("%s : Creating merchant ", name));
		
		Date date = new Date(System.currentTimeMillis());
		
		Language en = languageService.getByCode("en");
		Country ca = countryService.getByCode("CA");
		Currency currency = currencyService.getByCode("CAD");
		Zone qc = zoneService.getByCode("QC");
		
		List<Language> supportedLanguages = new ArrayList<Language>();
		supportedLanguages.add(en);
		
		//create a merchant
		MerchantStore store = new MerchantStore();
		store.setCountry(ca);
		store.setCurrency(currency);
		store.setDefaultLanguage(en);
		store.setInBusinessSince(date);
		store.setZone(qc);
		store.setStorename("Default store");
		store.setStorephone("888-888-8888");
		store.setCode(MerchantStore.DEFAULT_STORE);
		store.setStorecity("My city");
		store.setStoreaddress("1234 Street address");
		store.setStorepostalcode("H2H-2H2");
		store.setStoreEmailAddress("test@test.com");
		store.setDomainName("localhost:8080");
		store.setStoreTemplate("bootstrap3");
		store.setLanguages(supportedLanguages);
		
		merchantService.create(store);
		
		
		TaxClass taxclass = new TaxClass(TaxClass.DEFAULT_TAX_CLASS);
		taxclass.setMerchantStore(store);
		
		taxClassService.create(taxclass);
		
		
	}

	private void createModules() throws ServiceException {
		
		try {
			
			List<IntegrationModule> modules = modulesLoader.loadIntegrationModules("reference/integrationmodules-default.json");
            for (IntegrationModule entry : modules) {
        	    moduleConfigurationService.create(entry);
          }
			
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		
	}
	
	private void createSubReferences() throws ServiceException {
		
		LOG.info(String.format("%s : Loading catalog sub references ", name));
		
		ProductType productType = new ProductType();
		productType.setCode(ProductType.GENERAL_TYPE);
		productTypeService.create(productType);
		
	}
	
	private void createSecurityQuestions() throws ServiceException {
		List<SecurityQuestion> questions = new ArrayList<SecurityQuestion>();
		
		SecurityQuestion question = new SecurityQuestion("security.question.1");
		SecurityQuestion question2 = new SecurityQuestion("security.question.2");
		SecurityQuestion question3 = new SecurityQuestion("security.question.3");
		SecurityQuestion question4 = new SecurityQuestion("security.question.4");
		SecurityQuestion question5 = new SecurityQuestion("security.question.5");
		SecurityQuestion question6 = new SecurityQuestion("security.question.6");
		SecurityQuestion question7 = new SecurityQuestion("security.question.7");
		
		questions.add(question);
		questions.add(question2);
		questions.add(question3);
		questions.add(question4);
		questions.add(question5);
		questions.add(question6);
		questions.add(question7);
		
		securityQuestionService.save(questions);
		
	}
	



}
