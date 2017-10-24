package me.alanx.ecomer.core.services.customer.attribute;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.customer.attribute.CustomerOption;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;


public interface CustomerOptionService extends SalesManagerEntityService<Long, CustomerOption> {

	List<CustomerOption> listByStore(MerchantStore store, Language language)
			throws ServiceException;



	void saveOrUpdate(CustomerOption entity) throws ServiceException;



	CustomerOption getByCode(MerchantStore store, String optionCode);




}
