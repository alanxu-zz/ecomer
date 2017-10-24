package me.alanx.ecomer.web.populator.order;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.model.catalog.product.DigitalProduct;
import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.product.OrderProduct;
import me.alanx.ecomer.core.model.order.product.OrderProductAttribute;
import me.alanx.ecomer.core.model.order.product.OrderProductDownload;
import me.alanx.ecomer.core.model.order.product.OrderProductPrice;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.catalog.product.ProductService;
import me.alanx.ecomer.core.services.catalog.product.attribute.ProductOptionPriceService;
import me.alanx.ecomer.core.services.catalog.product.file.DigitalProductService;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;
import me.alanx.ecomer.web.constants.ApplicationConstants;
import me.alanx.ecomer.web.dto.catalog.product.attribute.ProductAttribute;
import me.alanx.ecomer.web.dto.order.PersistableOrderProduct;

public class PersistableOrderProductPopulator extends
		AbstractDataPopulator<PersistableOrderProduct, OrderProduct> {
	
	private ProductService productService;
	private DigitalProductService digitalProductService;
	private ProductOptionPriceService productAttributeService;


	public ProductOptionPriceService getProductAttributeService() {
		return productAttributeService;
	}

	public void setProductAttributeService(
			ProductOptionPriceService productAttributeService) {
		this.productAttributeService = productAttributeService;
	}

	public DigitalProductService getDigitalProductService() {
		return digitalProductService;
	}

	public void setDigitalProductService(DigitalProductService digitalProductService) {
		this.digitalProductService = digitalProductService;
	}

	/**
	 * Converts a ShoppingCartItem carried in the ShoppingCart to an OrderProduct
	 * that will be saved in the system
	 */
	@Override
	public OrderProduct populate(PersistableOrderProduct source, OrderProduct target,
			MerchantStore store, Language language) throws ConversionException {
		
		Validate.notNull(productService,"productService must be set");
		Validate.notNull(digitalProductService,"digitalProductService must be set");
		Validate.notNull(productAttributeService,"productAttributeService must be set");

		
		try {
			Product modelProduct = productService.getById(source.getProduct().getId());
			if(modelProduct==null) {
				throw new ConversionException("Cannot get product with id (productId) " + source.getProduct().getId());
			}
			
			if(modelProduct.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
				throw new ConversionException("Invalid product id " + source.getProduct().getId());
			}

			DigitalProduct digitalProduct = digitalProductService.getByProduct(store, modelProduct);
			
			if(digitalProduct!=null) {
				OrderProductDownload orderProductDownload = new OrderProductDownload();	
				orderProductDownload.setOrderProductFilename(digitalProduct.getProductFileName());
				orderProductDownload.setOrderProduct(target);
				orderProductDownload.setDownloadCount(0);
				orderProductDownload.setMaxdays(ApplicationConstants.MAX_DOWNLOAD_DAYS);
				target.getDownloads().add(orderProductDownload);
			}

			target.setOneTimeCharge(source.getPrice());	
			target.setProductName(source.getProduct().getDescription().getName());
			target.setProductQuantity(source.getOrderedQuantity());
			target.setSku(source.getProduct().getSku());
			
			OrderProductPrice orderProductPrice = new OrderProductPrice();
			orderProductPrice.setDefaultPrice(true);
			orderProductPrice.setProductPrice(source.getPrice());
			orderProductPrice.setOrderProduct(target);
			

			
			Set<OrderProductPrice> prices = new HashSet<OrderProductPrice>();
			prices.add(orderProductPrice);

			/** DO NOT SUPPORT MUTIPLE PRICES **/
/*			//Other prices
			List<FinalPrice> otherPrices = finalPrice.getAdditionalPrices();
			if(otherPrices!=null) {
				for(FinalPrice otherPrice : otherPrices) {
					OrderProductPrice other = orderProductPrice(otherPrice);
					other.setOrderProduct(target);
					prices.add(other);
				}
			}*/
			
			target.setPrices(prices);
			
			//OrderProductAttribute
			List<ProductAttribute> attributeItems = source.getAttributes();
			if(!CollectionUtils.isEmpty(attributeItems)) {
				Set<OrderProductAttribute> attributes = new HashSet<OrderProductAttribute>();
				for(ProductAttribute attribute : attributeItems) {
					OrderProductAttribute orderProductAttribute = new OrderProductAttribute();
					orderProductAttribute.setOrderProduct(target);
					Long id = attribute.getId();
					me.alanx.ecomer.core.model.catalog.product.option.ProductOptionPrice attr = productAttributeService.getById(id);
					if(attr==null) {
						throw new ConversionException("Attribute id " + id + " does not exists");
					}
					
					if(attr.getProduct().getMerchantStore().getId().intValue()!=store.getId().intValue()) {
						throw new ConversionException("Attribute id " + id + " invalid for this store");
					}
					
					orderProductAttribute.setProductAttributeIsFree(attr.isFree());
					orderProductAttribute.setProductAttributeName(attr.getProductOption().getDescriptionsSettoList().get(0).getName());
					orderProductAttribute.setProductAttributeValueName(attr.getProductOptionValue().getDescriptionsSettoList().get(0).getName());
					orderProductAttribute.setProductAttributePrice(attr.getOptionPrice());
					orderProductAttribute.setProductAttributeWeight(attr.getWeight());
					orderProductAttribute.setProductOptionId(attr.getProductOption().getId());
					orderProductAttribute.setProductOptionValueId(attr.getProductOptionValue().getId());
					attributes.add(orderProductAttribute);
				}
				target.setOrderAttributes(attributes);
			}

			
		} catch (Exception e) {
			throw new ConversionException(e);
		}
		
		
		return target;
	}

	@Override
	protected OrderProduct createTarget() {
		return null;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public ProductService getProductService() {
		return productService;
	}
	


}
