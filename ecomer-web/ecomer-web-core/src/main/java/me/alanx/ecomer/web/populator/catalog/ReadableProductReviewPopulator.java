package me.alanx.ecomer.web.populator.catalog;

import java.util.Set;

import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.model.catalog.product.ProductReview;
import me.alanx.ecomer.core.model.catalog.product.ProductReviewDescription;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;
import me.alanx.ecomer.web.dto.catalog.product.ReadableProductReview;
import me.alanx.ecomer.web.dto.customer.ReadableCustomer;
import me.alanx.ecomer.web.populator.customer.ReadableCustomerPopulator;
import me.alanx.ecomer.web.utils.DateUtil;

public class ReadableProductReviewPopulator extends
		AbstractDataPopulator<ProductReview, ReadableProductReview> {

	@Override
	public ReadableProductReview populate(ProductReview source,
			ReadableProductReview target, MerchantStore store, Language language)
			throws ConversionException {

		
		try {
			ReadableCustomerPopulator populator = new ReadableCustomerPopulator();
			ReadableCustomer customer = new ReadableCustomer();
			populator.populate(source.getCustomer(), customer, store, language);

			target.setDate(DateUtil.formatDate(source.getReviewDate()));
			target.setCustomer(customer);
			target.setRating(source.getReviewRating());
			target.setProductId(source.getProduct().getId());
			
			Set<ProductReviewDescription> descriptions = source.getDescriptions();
			if(descriptions!=null) {
				for(ProductReviewDescription description : descriptions) {
					target.setDescription(description.getDescription());
					target.setLanguage(description.getLanguage().getCode());
					break;
				}
			}

			return target;
			
		} catch (Exception e) {
			throw new ConversionException("Cannot populate ProductReview", e);
		}
		
		
		
	}

	@Override
	protected ReadableProductReview createTarget() {
		return null;
	}

}
