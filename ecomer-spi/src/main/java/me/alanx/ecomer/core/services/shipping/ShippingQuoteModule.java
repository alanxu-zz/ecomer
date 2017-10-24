package me.alanx.ecomer.core.services.shipping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import me.alanx.ecomer.core.exception.IntegrationException;
import me.alanx.ecomer.core.model.common.Delivery;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.shipping.PackageDetails;
import me.alanx.ecomer.core.model.shipping.ShippingConfiguration;
import me.alanx.ecomer.core.model.shipping.ShippingOption;
import me.alanx.ecomer.core.model.shipping.ShippingOrigin;
import me.alanx.ecomer.core.model.shipping.ShippingQuote;
import me.alanx.ecomer.core.model.system.CustomIntegrationConfiguration;
import me.alanx.ecomer.core.model.system.IntegrationConfiguration;
import me.alanx.ecomer.core.model.system.IntegrationModule;

public interface ShippingQuoteModule {
	
	public void validateModuleConfiguration(IntegrationConfiguration integrationConfiguration, MerchantStore store) throws IntegrationException;
	public CustomIntegrationConfiguration getCustomModuleConfiguration(MerchantStore store) throws IntegrationException;
	
	public List<ShippingOption> getShippingQuotes(ShippingQuote quote, List<PackageDetails> packages, BigDecimal orderTotal, Delivery delivery, ShippingOrigin origin, MerchantStore store, IntegrationConfiguration configuration, IntegrationModule module, ShippingConfiguration shippingConfiguration, Locale locale) throws IntegrationException;

}
