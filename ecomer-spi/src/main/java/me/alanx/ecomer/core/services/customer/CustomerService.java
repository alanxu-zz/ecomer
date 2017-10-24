package me.alanx.ecomer.core.services.customer;


import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.common.Address;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.customer.CustomerCriteria;
import me.alanx.ecomer.core.model.customer.CustomerList;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;



public interface CustomerService  extends SalesManagerEntityService<Long, Customer> {

	public List<Customer> getByName(String firstName);

	List<Customer> listByStore(MerchantStore store);

	Customer getByNick(String nick);
	void saveOrUpdate(Customer customer) throws ServiceException ;

	CustomerList listByStore(MerchantStore store, CustomerCriteria criteria);

	Customer getByNick(String nick, int storeId);

	/**
	 * Return an {@link com.salesmanager.core.business.common.model.Address} object from the client IP address. Uses underlying GeoLocation module
	 * @param store
	 * @param ipAddress
	 * @return
	 * @throws ServiceException
	 */
	Address getCustomerAddress(MerchantStore store, String ipAddress)
			throws ServiceException;


}
