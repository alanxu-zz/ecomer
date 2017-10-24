package me.alanx.ecomer.core.services.tax;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.OrderSummary;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.tax.TaxConfiguration;
import me.alanx.ecomer.core.model.tax.TaxItem;


public interface TaxService   {

	/**
	 * Retrieves tax configurations (TaxConfiguration) for a given MerchantStore
	 * @param store
	 * @return
	 * @throws ServiceException
	 */
	TaxConfiguration getTaxConfiguration(MerchantStore store)
			throws ServiceException;

	/**
	 * Saves ShippingConfiguration to MerchantConfiguration table
	 * @param shippingConfiguration
	 * @param store
	 * @throws ServiceException
	 */
	void saveTaxConfiguration(TaxConfiguration shippingConfiguration,
			MerchantStore store) throws ServiceException;

	/**
	 * Calculates tax over an OrderSummary
	 * @param orderSummary
	 * @param customer
	 * @param store
	 * @param locale
	 * @return
	 * @throws ServiceException
	 */
	List<TaxItem> calculateTax(OrderSummary orderSummary, Customer customer,
			MerchantStore store, Language language) throws ServiceException;


}
