package me.alanx.ecomer.core.services.shipping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import me.alanx.ecomer.core.exception.IntegrationException;
import me.alanx.ecomer.core.model.common.Delivery;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.shipping.PackageDetails;
import me.alanx.ecomer.core.model.shipping.ShippingConfiguration;
import me.alanx.ecomer.core.model.shipping.ShippingOrigin;
import me.alanx.ecomer.core.model.shipping.ShippingQuote;
import me.alanx.ecomer.core.model.system.IntegrationConfiguration;
import me.alanx.ecomer.core.model.system.IntegrationModule;

/**
 * Invoked before or after quote processing
 * @author carlsamson
 *
 */
public interface ShippingQuotePrePostProcessModule {
	
	
	public String getModuleCode();
	

	public void prePostProcessShippingQuotes(
			ShippingQuote quote, 
			List<PackageDetails> packages, 
			BigDecimal orderTotal, 
			Delivery delivery, 
			ShippingOrigin origin, 
			MerchantStore store, 
			IntegrationConfiguration globalShippingConfiguration, 
			IntegrationModule currentModule, 
			ShippingConfiguration shippingConfiguration, 
			List<IntegrationModule> allModules, Locale locale) throws IntegrationException;

}
