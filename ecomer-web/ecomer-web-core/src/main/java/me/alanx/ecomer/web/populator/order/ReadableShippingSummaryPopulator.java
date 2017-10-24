package me.alanx.ecomer.web.populator.order;

import org.apache.commons.lang3.Validate;

import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.shipping.ShippingSummary;
import me.alanx.ecomer.core.services.catalog.product.PricingService;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;
import me.alanx.ecomer.web.dto.customer.ReadableDelivery;
import me.alanx.ecomer.web.dto.order.ReadableShippingSummary;

public class ReadableShippingSummaryPopulator extends
		AbstractDataPopulator<ShippingSummary, ReadableShippingSummary> {
	
	private PricingService pricingService;

	@Override
	public ReadableShippingSummary populate(ShippingSummary source,
			ReadableShippingSummary target, MerchantStore store,
			Language language) throws ConversionException {
		
		Validate.notNull(pricingService,"PricingService must be set");
	
		try {
			
			target.setFreeShipping(source.isFreeShipping());
			target.setHandling(source.getHandling());
			target.setShipping(source.getShipping());
			target.setShippingModule(source.getShippingModule());
			target.setShippingOption(source.getShippingOption());
			target.setTaxOnShipping(source.isTaxOnShipping());
			target.setHandlingText(pricingService.getDisplayAmount(source.getHandling(), store));
			target.setShippingText(pricingService.getDisplayAmount(source.getShipping(), store));
			
			if(source.getDeliveryAddress()!=null) {
			
				ReadableDelivery deliveryAddress = new ReadableDelivery();
				deliveryAddress.setAddress(source.getDeliveryAddress().getAddress());
				deliveryAddress.setPostalCode(source.getDeliveryAddress().getPostalCode());
				deliveryAddress.setCity(source.getDeliveryAddress().getCity());
				if(source.getDeliveryAddress().getZone()!=null) {
					deliveryAddress.setZone(source.getDeliveryAddress().getZone().getCode());
				}
				if(source.getDeliveryAddress().getCountry()!=null) {
					deliveryAddress.setCountry(source.getDeliveryAddress().getCountry().getIsoCode());
				}
				deliveryAddress.setLatitude(source.getDeliveryAddress().getLatitude());
				deliveryAddress.setLongitude(source.getDeliveryAddress().getLongitude());
				deliveryAddress.setStateProvince(source.getDeliveryAddress().getState());
				
				target.setDelivery(deliveryAddress);
			}

			
		} catch(Exception e) {
			throw new ConversionException(e);
		}
		
		return target;
		
		
	}

	@Override
	protected ReadableShippingSummary createTarget() {
		return new 
				ReadableShippingSummary();
	}

	public PricingService getPricingService() {
		return pricingService;
	}

	public void setPricingService(PricingService pricingService) {
		this.pricingService = pricingService;
	}

}
