package me.alanx.ecomer.core.services.reference.country;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.reference.Country;
import me.alanx.ecomer.core.model.reference.CountryDescription;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.repositories.reference.country.CountryRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;

@Service("countryService")
public class CountryServiceImpl extends SalesManagerEntityServiceImpl<Integer, Country> implements CountryService {

	private static final Logger log = LoggerFactory.getLogger(CountryServiceImpl.class);

	private CountryRepository countryRepository;

	@Inject
	public CountryServiceImpl(CountryRepository countryRepository) {
		super(countryRepository);
		this.countryRepository = countryRepository;
	}

	public Country getByCode(String code) throws ServiceException {
		return countryRepository.findByIsoCode(code);
	}

	@Override
	public Country addCountryDescription(Country country, CountryDescription description) throws ServiceException {

		country.getDescriptions().add(description);
		description.setCountry(country);
		country = update(country);

		for (CountryDescription cd : country.getDescriptions()) {
			log.debug("---- descp id: {}, country id: {}", cd.getId(), cd.getCountry().getId());
		}

		return country;

	}

	@Override
	public Map<String, Country> getCountriesMap(Language language) throws ServiceException {

		List<Country> countries = this.getCountries(language);

		Map<String, Country> returnMap = new LinkedHashMap<String, Country>();

		for (Country country : countries) {
			returnMap.put(country.getIsoCode(), country);
		}

		return returnMap;
	}

	@Override
	public List<Country> getCountries(final List<String> isoCodes, final Language language) throws ServiceException {
		List<Country> countryList = getCountries(language);
		List<Country> requestedCountryList = new ArrayList<Country>();
		if (!CollectionUtils.isEmpty(countryList)) {
			for (Country c : countryList) {
				if (isoCodes.contains(c.getIsoCode())) {
					requestedCountryList.add(c);
				}
			}
		}
		return requestedCountryList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Country> getCountries(Language language) throws ServiceException {

		List<Country> countries = null;
		try {

			log.trace("Get contries by langurage id {}...", language.getId());
			countries = countryRepository.listByLanguage(language.getId());

			log.trace("{} countries fetched", countries != null ? countries.size(): null);
			// set names
			for (Country country : countries) {

				CountryDescription description = country.getDescriptions().get(0);
				country.setName(description.getName());

			}

		} catch (Exception e) {
			log.error("getCountries()", e);
		}

		return countries;

	}

}
