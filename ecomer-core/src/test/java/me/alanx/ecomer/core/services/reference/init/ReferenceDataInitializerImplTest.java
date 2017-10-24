package me.alanx.ecomer.core.services.reference.init;

import static org.junit.Assert.*;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import me.alanx.ecomer.core.services.catalog.product.type.ProductTypeService;
import me.alanx.ecomer.core.services.merchant.MerchantStoreService;
import me.alanx.ecomer.core.services.reference.country.CountryService;
import me.alanx.ecomer.core.services.reference.currency.CurrencyService;
import me.alanx.ecomer.core.services.reference.language.LanguageService;
import me.alanx.ecomer.core.services.reference.loader.IntegrationModulesLoader;
import me.alanx.ecomer.core.services.reference.loader.ZonesLoader;
import me.alanx.ecomer.core.services.reference.zone.ZoneService;
import me.alanx.ecomer.core.services.system.ModuleConfigurationService;
import me.alanx.ecomer.core.services.tax.TaxClassService;
import me.alanx.ecomer.test.SpringJUnitTestBase;

public class ReferenceDataInitializerImplTest extends SpringJUnitTestBase{
	
	
	private static final Logger log = LoggerFactory.getLogger(ReferenceDataInitializerImplTest.class);

	
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
	
	@Autowired
	private void fixture(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void checkInitialData() {
		int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "LANGUAGES");
		log.info("LANGUAGES counts: {} ", count);
		assertTrue(count > 0);
	}
	
}
