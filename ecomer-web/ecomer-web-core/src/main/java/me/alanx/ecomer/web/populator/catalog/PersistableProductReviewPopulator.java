package me.alanx.ecomer.web.populator.catalog;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;

import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.ProductReview;
import me.alanx.ecomer.core.model.catalog.product.ProductReviewDescription;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.catalog.product.ProductService;
import me.alanx.ecomer.core.services.customer.CustomerService;
import me.alanx.ecomer.core.services.reference.language.LanguageService;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;
import me.alanx.ecomer.web.dto.catalog.product.PersistableProductReview;
import me.alanx.ecomer.web.utils.DateUtil;



public class PersistableProductReviewPopulator extends
		AbstractDataPopulator<PersistableProductReview, ProductReview> {
	
	
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	private ProductService productService;
	
	@Inject
	private LanguageService languageService;
	


	public LanguageService getLanguageService() {
		return languageService;
	}

	public void setLanguageService(LanguageService languageService) {
		this.languageService = languageService;
	}

	@Override
	public ProductReview populate(PersistableProductReview source,
			ProductReview target, MerchantStore store, Language language)
			throws ConversionException {
		
		
		Validate.notNull(customerService,"customerService cannot be null");
		Validate.notNull(productService,"productService cannot be null");
		Validate.notNull(languageService,"languageService cannot be null");
		
		try {
			
			if(target==null) {
				target = new ProductReview();
			}
			
			Customer customer = customerService.getById(source.getCustomerId());
			
			//check if customer belongs to store
			if(customer ==null || customer.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
				throw new ConversionException("Invalid customer id for the given store");
			}
			
			target.setReviewDate(DateUtil.getDate(source.getDate()));
			target.setCustomer(customer);
			target.setReviewRating(source.getRating());
			
			Product product = productService.getById(source.getProductId());
			
			//check if product belongs to store
			if(product ==null || product.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
				throw new ConversionException("Invalid product id for the given store");
			}
			
			target.setProduct(product);
			
			Language lang = languageService.getByCode(language.getCode());
			if(lang ==null) {
				throw new ConversionException("Invalid language code, use iso codes (en, fr ...)");
			}
			
			ProductReviewDescription description = new ProductReviewDescription();
			description.setDescription(source.getDescription());
			description.setLanguage(lang);
			description.setName("-");
			description.setProductReview(target);
			
			Set<ProductReviewDescription> descriptions = new HashSet<ProductReviewDescription>();
			descriptions.add(description);
			
			target.setDescriptions(descriptions);
			
			

			
			
			return target;
			
		} catch (Exception e) {
			throw new ConversionException("Cannot populate ProductReview", e);
		}
		
	}

	@Override
	protected ProductReview createTarget() {
		return null;
	}
	
	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}


}
