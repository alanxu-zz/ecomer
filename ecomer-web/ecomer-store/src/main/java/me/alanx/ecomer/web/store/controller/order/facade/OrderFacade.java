package me.alanx.ecomer.web.store.controller.order.facade;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.validation.BindingResult;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.Order;
import me.alanx.ecomer.core.model.order.OrderTotalSummary;
import me.alanx.ecomer.core.model.payments.Transaction;
import me.alanx.ecomer.core.model.reference.Country;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.shipping.ShippingQuote;
import me.alanx.ecomer.core.model.shipping.ShippingSummary;
import me.alanx.ecomer.core.model.shoppingcart.ShoppingCart;
import me.alanx.ecomer.web.dto.customer.PersistableCustomer;
import me.alanx.ecomer.web.dto.order.PersistableOrder;
import me.alanx.ecomer.web.dto.order.ReadableOrder;
import me.alanx.ecomer.web.dto.order.ReadableOrderList;
import me.alanx.ecomer.web.dto.order.ShopOrder;


public interface OrderFacade {
	
	ShopOrder initializeOrder(MerchantStore store, Customer customer, ShoppingCart shoppingCart, Language language) throws Exception;
	void refreshOrder(ShopOrder order, MerchantStore store, Customer customer, ShoppingCart shoppingCart, Language language) throws Exception;
	/** used in website **/
	OrderTotalSummary calculateOrderTotal(MerchantStore store, ShopOrder order, Language language) throws Exception;
	/** used in the API **/
	OrderTotalSummary calculateOrderTotal(MerchantStore store, PersistableOrder order, Language language) throws Exception;

	/** process a valid order **/
	Order processOrder(ShopOrder order, Customer customer, MerchantStore store, Language language) throws ServiceException;
	/** process a valid order against an initial transaction **/
	Order processOrder(ShopOrder order, Customer customer, Transaction transaction, MerchantStore store, Language language) throws ServiceException;
	
	/** creates a working copy of customer when the user is anonymous **/
	Customer initEmptyCustomer(MerchantStore store);
	List<Country> getShipToCountry(MerchantStore store, Language language)
			throws Exception;
	
	/**
	 * Get a ShippingQuote based on merchant configuration and items to be shipped
	 * @param cart
	 * @param order
	 * @param store
	 * @param language
	 * @return
	 * @throws Exception
	 */
	ShippingQuote getShippingQuote(PersistableCustomer customer, ShoppingCart cart, ShopOrder order,
			MerchantStore store, Language language) throws Exception;
	
	ShippingQuote getShippingQuote(Customer customer, ShoppingCart cart, PersistableOrder order,
			MerchantStore store, Language language) throws Exception;
	
	/**
	 * Creates a ShippingSummary object for OrderTotal calculation based on a ShippingQuote
	 * @param quote
	 * @param store
	 * @param language
	 * @return
	 */
	ShippingSummary getShippingSummary(ShippingQuote quote, MerchantStore store, Language language);
	void validateOrder(ShopOrder order, BindingResult bindingResult,
			Map<String, String> messagesResult, MerchantStore store,
			Locale locale) throws ServiceException;
	
	/**
	 * Creates a ReadableOrder object from an orderId
	 * @param orderId
	 * @param store
	 * @param language
	 * @return
	 * @throws Exception
	 */
	ReadableOrder getReadableOrder(Long orderId, MerchantStore store, Language language) throws Exception;
	

	/**
     * <p>Method used to fetch all orders associated with customer customer.
     * It will used current customer ID to fetch all orders which has been 
     * placed by customer for current store.</p>
     * 
     * @param customer currently logged in customer 
     * @param store store associated with current customer
     * @return ReadableOrderList
     * @throws Exception
     */
    
	ReadableOrderList getReadableOrderList(MerchantStore store, Customer customer, int start,
			int maxCount, Language language) throws Exception;
	
	
	/**
	 * Get orders for a given store
	 * @param store
	 * @param start
	 * @param maxCount
	 * @param language
	 * @return
	 * @throws Exception
	 */
	ReadableOrderList getReadableOrderList(MerchantStore store, int start,
			int maxCount, Language language) throws Exception;
}
