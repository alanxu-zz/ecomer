package me.alanx.ecomer.core.services.customer.attribute;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.customer.attribute.CustomerOption;
import me.alanx.ecomer.core.model.customer.attribute.CustomerOptionSet;
import me.alanx.ecomer.core.model.customer.attribute.CustomerOptionValue;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;


public interface CustomerOptionSetService extends SalesManagerEntityService<Long, CustomerOptionSet> {



	void saveOrUpdate(CustomerOptionSet entity) throws ServiceException;




	List<CustomerOptionSet> listByStore(MerchantStore store,
			Language language) throws ServiceException;




	List<CustomerOptionSet> listByOption(CustomerOption option,
			MerchantStore store) throws ServiceException;
	

	List<CustomerOptionSet> listByOptionValue(CustomerOptionValue optionValue,
			MerchantStore store) throws ServiceException;

}
