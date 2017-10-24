package me.alanx.ecomer.web.populator.order;

import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.product.OrderProductDownload;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;
import me.alanx.ecomer.web.dto.order.ReadableOrderProductDownload;

public class ReadableOrderProductDownloadPopulator extends
		AbstractDataPopulator<OrderProductDownload, ReadableOrderProductDownload> {

	@Override
	public ReadableOrderProductDownload populate(OrderProductDownload source,
			ReadableOrderProductDownload target, MerchantStore store,
			Language language) throws ConversionException {
		try {
			
			target.setProductName(source.getOrderProduct().getProductName());
			target.setDownloadCount(source.getDownloadCount());
			target.setDownloadExpiryDays(source.getMaxdays());
			target.setId(source.getId());
			target.setFileName(source.getOrderProductFilename());
			target.setOrderId(source.getOrderProduct().getOrder().getId());
			
			return target;
			
		} catch(Exception e) {
			throw new ConversionException(e);
		}
	}

	@Override
	protected ReadableOrderProductDownload createTarget() {
		return new ReadableOrderProductDownload();
	}
	

}
