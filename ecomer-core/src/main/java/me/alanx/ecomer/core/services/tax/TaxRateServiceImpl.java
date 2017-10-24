package me.alanx.ecomer.core.services.tax;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Country;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.reference.Zone;
import me.alanx.ecomer.core.model.tax.taxclass.TaxClass;
import me.alanx.ecomer.core.model.tax.taxrate.TaxRate;
import me.alanx.ecomer.core.repositories.tax.TaxRateRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;

@Service("taxRateService")
public class TaxRateServiceImpl extends SalesManagerEntityServiceImpl<Long, TaxRate>
		implements TaxRateService {

	private TaxRateRepository taxRateRepository;
	
	@Inject
	public TaxRateServiceImpl(TaxRateRepository taxRateRepository) {
		super(taxRateRepository);
		this.taxRateRepository = taxRateRepository;
	}

	@Override
	public List<TaxRate> listByStore(MerchantStore store)
			throws ServiceException {
		return taxRateRepository.findByStore(store.getId());
	}
	
	@Override
	public List<TaxRate> listByStore(MerchantStore store, Language language)
			throws ServiceException {
		return taxRateRepository.findByStoreAndLanguage(store.getId(), language.getId());
	}
	
	
	@Override
	public TaxRate getByCode(String code, MerchantStore store)
			throws ServiceException {
		return taxRateRepository.findByStoreAndCode(store.getId(), code);
	}
	
	@Override
	public List<TaxRate> listByCountryZoneAndTaxClass(Country country, Zone zone, TaxClass taxClass, MerchantStore store, Language language) throws ServiceException {
		//return taxRateDao.listByCountryZoneAndTaxClass(country, zone, taxClass, store, language);
		return taxRateRepository.findByMerchantAndZoneAndCountryAndLanguage(store.getId(), zone.getId(), country.getId(), language.getId());
	}
	
	@Override
	public List<TaxRate> listByCountryStateProvinceAndTaxClass(Country country, String stateProvince, TaxClass taxClass, MerchantStore store, Language language) throws ServiceException {
		//return taxRateDao.listByCountryStateProvinceAndTaxClass(country, stateProvince, taxClass, store, language);
		return taxRateRepository.findByMerchantAndProvinceAndCountryAndLanguage(store.getId(), stateProvince, country.getId(), language.getId());
	}
	
	@Override
	public void delete(TaxRate taxRate) throws ServiceException {
		
		//TaxRate t = this.getById(taxRate.getId());
		//super.delete(t);
		taxRateRepository.delete(taxRate);
		
	}
		

	
}
