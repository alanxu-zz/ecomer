package me.alanx.ecomer.web.populator.order;

import org.apache.commons.lang3.Validate;

import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOptionPrice;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem;
import me.alanx.ecomer.core.services.catalog.product.ProductService;
import me.alanx.ecomer.core.services.catalog.product.attribute.ProductOptionPriceService;
import me.alanx.ecomer.core.services.shoppingcart.ShoppingCartService;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;
import me.alanx.ecomer.web.dto.order.PersistableOrderProduct;

public class ShoppingCartItemPopulator extends
		AbstractDataPopulator<PersistableOrderProduct, ShoppingCartItem> {
	

	private ProductService productService;
	private ProductOptionPriceService productAttributeService;
	private ShoppingCartService shoppingCartService;

	@Override
	public ShoppingCartItem populate(PersistableOrderProduct source,
			ShoppingCartItem target, MerchantStore store, Language language)
			throws ConversionException {
		Validate.notNull(productService, "Requires to set productService");
		Validate.notNull(productAttributeService, "Requires to set productAttributeService");
		Validate.notNull(shoppingCartService, "Requires to set shoppingCartService");
		
		Product product = productService.getById(source.getProduct().getId());
		if(source.getAttributes()!=null) {

			for(me.alanx.ecomer.web.dto.catalog.product.attribute.ProductAttribute attr : source.getAttributes()) {
				ProductOptionPrice attribute = productAttributeService.getById(attr.getId());
				if(attribute==null) {
					throw new ConversionException("ProductAttribute with id " + attr.getId() + " is null");
				}
				if(attribute.getProduct().getId().longValue()!=source.getProduct().getId().longValue()) {
					throw new ConversionException("ProductAttribute with id " + attr.getId() + " is not assigned to Product id " + source.getProduct().getId());
				}
				product.getOptions().add(attribute);
			}
		}
		
		try {
			return shoppingCartService.populateShoppingCartItem(product);
		} catch (ServiceException e) {
			throw new ConversionException(e);
		}
		
	}

	@Override
	protected ShoppingCartItem createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setProductAttributeService(ProductOptionPriceService productAttributeService) {
		this.productAttributeService = productAttributeService;
	}

	public ProductOptionPriceService getProductAttributeService() {
		return productAttributeService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setShoppingCartService(ShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}

	public ShoppingCartService getShoppingCartService() {
		return shoppingCartService;
	}

}
