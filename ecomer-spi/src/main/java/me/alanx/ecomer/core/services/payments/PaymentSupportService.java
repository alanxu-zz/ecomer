package me.alanx.ecomer.core.services.payments;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.Order;
import me.alanx.ecomer.core.model.payments.CreditCardType;
import me.alanx.ecomer.core.model.payments.Payment;
import me.alanx.ecomer.core.model.payments.PaymentMethod;
import me.alanx.ecomer.core.model.payments.Transaction;
import me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem;
import me.alanx.ecomer.core.model.system.IntegrationConfiguration;
import me.alanx.ecomer.core.model.system.IntegrationModule;

public interface PaymentSupportService {



	public List<IntegrationModule> getPaymentMethods(MerchantStore store)
			throws ServiceException;

	Map<String, IntegrationConfiguration> getPaymentModulesConfigured(
			MerchantStore store) throws ServiceException;
	
	Transaction processPayment(Customer customer, MerchantStore store, Payment payment, List<ShoppingCartItem> items, Order order) throws ServiceException;
	Transaction processRefund(Order order, Customer customer, MerchantStore store, BigDecimal amount) throws ServiceException;

	/**
	 * Get a specific Payment module by payment type CREDITCART, MONEYORDER ...
	 * @param store
	 * @param type (payment type)
	 * @return IntegrationModule
	 * @throws ServiceException
	 */
	IntegrationModule getPaymentMethodByType(MerchantStore store, String type)
			throws ServiceException;
	
	/**
	 * Get a specific Payment module by payment code (defined in integrationmoduel.json) paypal, authorizenet ..
	 * @param store
	 * @param name
	 * @return IntegrationModule
	 * @throws ServiceException
	 */
	IntegrationModule getPaymentMethodByCode(MerchantStore store, String name)
			throws ServiceException;

	/**
	 * Saves a payment module configuration
	 * @param configuration
	 * @param store
	 * @throws ServiceException
	 */
	void savePaymentModuleConfiguration(IntegrationConfiguration configuration,
			MerchantStore store) throws ServiceException;

	/**
	 * Validates if the credit card input information are correct
	 * @param number
	 * @param type
	 * @param month
	 * @param date
	 * @throws ServiceException
	 */
	void validateCreditCard(String number, CreditCardType creditCard, String month, String date)
			throws ServiceException;

	/**
	 * Get the integration configuration
	 * for a specific payment module
	 * @param moduleCode
	 * @param store
	 * @return IntegrationConfiguration
	 * @throws ServiceException
	 */
	IntegrationConfiguration getPaymentConfiguration(String moduleCode,
			MerchantStore store) throws ServiceException;

	void removePaymentModuleConfiguration(String moduleCode, MerchantStore store)
			throws ServiceException;

	Transaction processCapturePayment(Order order, Customer customer,
			MerchantStore store)
			throws ServiceException;

	List<PaymentMethod> getAcceptedPaymentMethods(MerchantStore store)
			throws ServiceException;

	/**
	 * Returns a PaymentModule based on the payment code
	 * @param paymentModuleCode
	 * @return PaymentModule
	 * @throws ServiceException
	 */
	PaymentService getPaymentModule(String paymentModuleCode)
			throws ServiceException;

}