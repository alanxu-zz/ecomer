package me.alanx.ecomer.core.services.customer.attribute;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.customer.attribute.CustomerAttribute;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;



public interface CustomerAttributeService extends
		SalesManagerEntityService<Long, CustomerAttribute> {

	void saveOrUpdate(CustomerAttribute customerAttribute)
			throws ServiceException;

	CustomerAttribute getByCustomerOptionId(MerchantStore store,
			Long customerId, Long id);

	List<CustomerAttribute> getByCustomerOptionValueId(MerchantStore store,
			Long id);

	List<CustomerAttribute> getByOptionId(MerchantStore store, Long id);


	List<CustomerAttribute> getByCustomer(MerchantStore store, Customer customer);
	

}
