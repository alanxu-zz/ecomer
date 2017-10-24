package me.alanx.ecomer.core.services.shipping;

import java.util.List;
import java.util.Map;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.common.Delivery;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Country;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.shipping.PackageDetails;
import me.alanx.ecomer.core.model.shipping.ShippingConfiguration;
import me.alanx.ecomer.core.model.shipping.ShippingMetaData;
import me.alanx.ecomer.core.model.shipping.ShippingOption;
import me.alanx.ecomer.core.model.shipping.ShippingProduct;
import me.alanx.ecomer.core.model.shipping.ShippingQuote;
import me.alanx.ecomer.core.model.shipping.ShippingSummary;
import me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem;
import me.alanx.ecomer.core.model.system.CustomIntegrationConfiguration;
import me.alanx.ecomer.core.model.system.IntegrationConfiguration;
import me.alanx.ecomer.core.model.system.IntegrationModule;



public interface ShippingService {

	/**
	 * Returns a list of supported countries (ship to country list) configured by merchant
	 * when the merchant configured shipping National and has saved a list of ship to country
	 * from the list
	 * @param store
	 * @return
	 * @throws ServiceException
	 */
	public  List<String> getSupportedCountries(MerchantStore store)
			throws ServiceException;

	public  void setSupportedCountries(MerchantStore store,
			List<String> countryCodes) throws ServiceException;

	/**
	 * Returns a list of available shipping modules
	 * @param store
	 * @return
	 * @throws ServiceException
	 */
	public List<IntegrationModule> getShippingMethods(MerchantStore store)
			throws ServiceException;

	
	/**
	 * Returns a list of configured shipping modules for a given merchant
	 * @param store
	 * @return
	 * @throws ServiceException
	 */
	Map<String, IntegrationConfiguration> getShippingModulesConfigured(
			MerchantStore store) throws ServiceException;

	/**
	 * Adds a Shipping configuration
	 * @param configuration
	 * @param store
	 * @throws ServiceException
	 */
	void saveShippingQuoteModuleConfiguration(IntegrationConfiguration configuration,
			MerchantStore store) throws ServiceException;

	/**
	 * ShippingType (NATIONAL, INTERNATIONSL)
	 * ShippingBasisType (SHIPPING, BILLING)
	 * ShippingPriceOptionType (ALL, LEAST, HIGHEST)
	 * Packages
	 * Handling
	 * @param store
	 * @return
	 * @throws ServiceException
	 */
	ShippingConfiguration getShippingConfiguration(MerchantStore store)
			throws ServiceException;

	/**
	 * Saves ShippingConfiguration for a given MerchantStore
	 * @param shippingConfiguration
	 * @param store
	 * @throws ServiceException
	 */
	void saveShippingConfiguration(ShippingConfiguration shippingConfiguration,
			MerchantStore store) throws ServiceException;

	void removeShippingQuoteModuleConfiguration(String moduleCode,
			MerchantStore store) throws ServiceException;

	/**
	 * Provides detailed information on boxes that will be used
	 * when getting a ShippingQuote
	 * @param products
	 * @param store
	 * @return
	 * @throws ServiceException
	 */
	List<PackageDetails> getPackagesDetails(List<ShippingProduct> products,
			MerchantStore store) throws ServiceException;

	/**
	 * Get a list of ShippingQuote from a configured
	 * shipping gateway. The quotes are displayed to the end user so he can pick
	 * the ShippingOption he wants
	 * @param store
	 * @param customer
	 * @param products
	 * @param language
	 * @return
	 * @throws ServiceException
	 */
	ShippingQuote getShippingQuote(MerchantStore store, Delivery delivery,
			List<ShippingProduct> products, Language language)
			throws ServiceException;
	

	/**
	 * Returns a shipping module configuration given a moduleCode
	 * @param moduleCode
	 * @param store
	 * @return
	 * @throws ServiceException
	 */
	IntegrationConfiguration getShippingConfiguration(String moduleCode,
			MerchantStore store) throws ServiceException;
	
	/**
	 * Retrieves the custom configuration for a given module
	 * @param moduleCode
	 * @param store
	 * @return
	 * @throws ServiceException
	 */


	CustomIntegrationConfiguration getCustomShippingConfiguration(
			String moduleCode, MerchantStore store) throws ServiceException;

	/**
	 * Weight based configuration
	 * @param moduleCode
	 * @param shippingConfiguration
	 * @param store
	 * @throws ServiceException
	 */
	void saveCustomShippingConfiguration(String moduleCode,
			CustomIntegrationConfiguration shippingConfiguration,
			MerchantStore store) throws ServiceException;

	/**
	 * Removes a custom shipping quote
	 * module
	 * @param moduleCode
	 * @param store
	 * @throws ServiceException
	 */
	void removeCustomShippingQuoteModuleConfiguration(String moduleCode,
			MerchantStore store) throws ServiceException;

	/**
	 * The {@link ShippingSummary} is built from the ShippingOption the user has selected
	 * The ShippingSummary is used for order calculation
	 * @param store
	 * @param shippingQuote
	 * @param selectedShippingOption
	 * @return
	 * @throws ServiceException
	 */
	ShippingSummary getShippingSummary(MerchantStore store, ShippingQuote shippingQuote, 
			ShippingOption selectedShippingOption) throws ServiceException;

	/**
	 * Returns a list of supported countries (ship to country list) configured by merchant
	 * If the merchant configured shipping National, then only store country will be in the list
	 * If the merchant configured shipping International, then the list of accepted country is returned
	 * from the list
	 * @param store
	 * @param language
	 * @return
	 * @throws ServiceException
	 */
	List<Country> getShipToCountryList(MerchantStore store, Language language)
			throws ServiceException;
	
	/**
	 * Determines if Shipping should be proposed to the customer
	 * @param items
	 * @param store
	 * @return
	 * @throws ServiceException
	 */
	boolean requiresShipping(List<ShoppingCartItem> items, MerchantStore store) throws ServiceException;

	/**
	 * Returns shipping metadata and how shipping is configured for a given store
	 * @param store
	 * @return
	 * @throws ServiceException
	 */
	ShippingMetaData getShippingMetaData(MerchantStore store) throws ServiceException;


}