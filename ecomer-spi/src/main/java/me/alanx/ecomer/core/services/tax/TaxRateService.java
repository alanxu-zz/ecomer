package me.alanx.ecomer.core.services.tax;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Country;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.reference.Zone;
import me.alanx.ecomer.core.model.tax.taxclass.TaxClass;
import me.alanx.ecomer.core.model.tax.taxrate.TaxRate;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;

public interface TaxRateService extends SalesManagerEntityService<Long, TaxRate> {

	public List<TaxRate> listByStore(MerchantStore store) throws ServiceException;

	List<TaxRate> listByCountryZoneAndTaxClass(Country country, Zone zone,
			TaxClass taxClass, MerchantStore store, Language language)
			throws ServiceException;

	List<TaxRate> listByCountryStateProvinceAndTaxClass(Country country,
			String stateProvince, TaxClass taxClass, MerchantStore store,
			Language language) throws ServiceException;

	 TaxRate getByCode(String code, MerchantStore store)
			throws ServiceException;

	List<TaxRate> listByStore(MerchantStore store, Language language)
			throws ServiceException;
	
	

}
