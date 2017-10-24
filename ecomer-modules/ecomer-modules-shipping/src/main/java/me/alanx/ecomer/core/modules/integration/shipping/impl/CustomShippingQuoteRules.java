package me.alanx.ecomer.core.modules.integration.shipping.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.drools.KnowledgeBase;
import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import me.alanx.ecomer.core.services.shipping.ShippingQuoteModule;


public class CustomShippingQuoteRules implements ShippingQuoteModule {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomShippingQuoteRules.class);
	
	
	private StatelessKnowledgeSession shippingPriceRule;
	
	public final static String MODULE_CODE = "customQuotesRules";
	
	private KnowledgeBase kbase;

	@Override
	public void validateModuleConfiguration(
			IntegrationConfiguration integrationConfiguration,
			MerchantStore store) throws IntegrationException {
		// Not used

	}

	@Override
	public CustomIntegrationConfiguration getCustomModuleConfiguration(
			MerchantStore store) throws IntegrationException {
		// Not used
		return null;
	}

	@Override
	public List<ShippingOption> getShippingQuotes(ShippingQuote quote,
			List<PackageDetails> packages, BigDecimal orderTotal,
			Delivery delivery, ShippingOrigin origin, MerchantStore store,
			IntegrationConfiguration configuration, IntegrationModule module,
			ShippingConfiguration shippingConfiguration, Locale locale)
			throws IntegrationException {

		
		
		Validate.notNull(delivery, "Delivery cannot be null");
		Validate.notNull(delivery.getCountry(), "Delivery.country cannot be null");
		Validate.notNull(packages, "packages cannot be null");
		Validate.notEmpty(packages, "packages cannot be empty");
		
		//requires the postal code
		if(StringUtils.isBlank(delivery.getPostalCode())) {
			return null;
		}

		Double distance = null;
		
		if(quote!=null) {
			//look if distance has been calculated
			if(quote.getQuoteInformations()!=null) {
				if(quote.getQuoteInformations().containsKey(Constants.DISTANCE_KEY)) {
					distance = (Double)quote.getQuoteInformations().get(Constants.DISTANCE_KEY);
				}
			}
		}
		
		//calculate volume (L x W x H)
		Double volume = null;
		Double weight = 0D;
		Double size = null;
		//calculate weight
		for(PackageDetails pack : packages) {
			weight = weight + pack.getShippingWeight();
			Double tmpVolume = pack.getShippingHeight() * pack.getShippingLength() * pack.getShippingWidth();
			if(volume == null || tmpVolume.doubleValue() > volume.doubleValue()) { //take the largest volume
				volume = tmpVolume;
			} 
			//largest size
			List<Double> sizeList = new ArrayList<Double>();
			sizeList.add(pack.getShippingHeight());
			sizeList.add(pack.getShippingWeight());
			sizeList.add(pack.getShippingLength());
			Double maxSize = (Double)Collections.max(sizeList);
			if(size==null || maxSize.doubleValue() > size.doubleValue()) {
				size = maxSize.doubleValue();
			}
		}
		
		//Build a ShippingInputParameters
		ShippingInputParameters inputParameters = new ShippingInputParameters();
		
		inputParameters.setWeight((long)weight.doubleValue());
		inputParameters.setCountry(delivery.getCountry().getIsoCode());
		inputParameters.setProvince("*");
		inputParameters.setModuleName(module.getCode());
		
		if(delivery.getZone().getCode()!=null) {
			inputParameters.setProvince(delivery.getZone().getCode());
		}
		
		if(distance!=null) {
			double ddistance = distance.doubleValue();
			long ldistance = (long)ddistance;
			inputParameters.setDistance(ldistance);
		}
		
		if(volume!=null) {
			inputParameters.setVolume((long)volume.doubleValue());
		}
		
		List<ShippingOption> options = quote.getShippingOptions();
		
		if(options == null) {
			options = new ArrayList<ShippingOption>();
			quote.setShippingOptions(options);
		}
		
		
		
		LOGGER.debug("Setting input parameters " + inputParameters.toString());
		
		shippingPriceRule.execute(Arrays.asList(new Object[] { inputParameters }));

		if(inputParameters.getPriceQuote() != null) {

			ShippingOption shippingOption = new ShippingOption();
			
			
			shippingOption.setOptionPrice(new BigDecimal(inputParameters.getPriceQuote()));
			shippingOption.setShippingModuleCode(MODULE_CODE);
			shippingOption.setOptionCode(MODULE_CODE);
			shippingOption.setOptionId(MODULE_CODE);

			options.add(shippingOption);
		}

		
		return options;
		
		
	}

	public StatelessKnowledgeSession getShippingPriceRule() {
		return shippingPriceRule;
	}

	public void setShippingPriceRule(StatelessKnowledgeSession shippingPriceRule) {
		this.shippingPriceRule = shippingPriceRule;
	}

	public KnowledgeBase getKbase() {
		return kbase;
	}

	public void setKbase(KnowledgeBase kbase) {
		this.kbase = kbase;
	}

}
