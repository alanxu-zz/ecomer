package me.alanx.ecomer.core.repositories.customer;

import me.alanx.ecomer.core.model.customer.CustomerCriteria;
import me.alanx.ecomer.core.model.customer.CustomerList;
import me.alanx.ecomer.core.model.merchant.MerchantStore;



public interface CustomerRepositoryCustom {

	CustomerList listByStore(MerchantStore store, CustomerCriteria criteria);
	

}
