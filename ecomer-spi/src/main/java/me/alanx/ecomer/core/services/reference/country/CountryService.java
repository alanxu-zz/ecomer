package me.alanx.ecomer.core.services.reference.country;

import java.util.List;
import java.util.Map;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.reference.Country;
import me.alanx.ecomer.core.model.reference.CountryDescription;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;

public interface CountryService extends SalesManagerEntityService<Integer, Country> {

	public Country getByCode(String code) throws ServiceException;
	
	public Country addCountryDescription(Country country, CountryDescription description) throws ServiceException;

	public List<Country> getCountries(Language language) throws ServiceException;

	Map<String, Country> getCountriesMap(Language language)
			throws ServiceException;

	List<Country> getCountries(List<String> isoCodes, Language language)
			throws ServiceException;
}
