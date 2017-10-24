package me.alanx.ecomer.web.dto.admin.catalog;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import me.alanx.ecomer.core.model.catalog.product.ProductAvailability;
import me.alanx.ecomer.core.model.catalog.product.price.ProductPriceDescription;

public class ProductPrice {
	
	@Valid
	private me.alanx.ecomer.core.model.catalog.product.price.ProductPrice price = null;
	@Valid
	private List <ProductPriceDescription> descriptions = new ArrayList<ProductPriceDescription>();
	private String priceText;
	private String specialPriceText;
	private ProductAvailability productAvailability;
	
	
	//cannot convert in this object to date ??? needs to use a string, parse, bla bla
	private String productPriceSpecialStartDate;
	private String productPriceSpecialEndDate;
	
	private me.alanx.ecomer.core.model.catalog.product.Product product;
	
	
	
	
	
	public List <ProductPriceDescription> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(List <ProductPriceDescription> descriptions) {
		this.descriptions = descriptions;
	}
	public ProductAvailability getProductAvailability() {
		return productAvailability;
	}
	public void setProductAvailability(ProductAvailability productAvailability) {
		this.productAvailability = productAvailability;
	}
	public String getPriceText() {
		return priceText;
	}
	public void setPriceText(String priceText) {
		this.priceText = priceText;
	}
	public me.alanx.ecomer.core.model.catalog.product.price.ProductPrice getPrice() {
		return price;
	}
	public void setPrice(me.alanx.ecomer.core.model.catalog.product.price.ProductPrice price) {
		this.price = price;
	}
	public String getSpecialPriceText() {
		return specialPriceText;
	}
	public void setSpecialPriceText(String specialPriceText) {
		this.specialPriceText = specialPriceText;
	}

	public me.alanx.ecomer.core.model.catalog.product.Product getProduct() {
		return product;
	}
	public void setProduct(me.alanx.ecomer.core.model.catalog.product.Product product) {
		this.product = product;
	}
	public String getProductPriceSpecialStartDate() {
		return productPriceSpecialStartDate;
	}
	public void setProductPriceSpecialStartDate(
			String productPriceSpecialStartDate) {
		this.productPriceSpecialStartDate = productPriceSpecialStartDate;
	}
	public String getProductPriceSpecialEndDate() {
		return productPriceSpecialEndDate;
	}
	public void setProductPriceSpecialEndDate(String productPriceSpecialEndDate) {
		this.productPriceSpecialEndDate = productPriceSpecialEndDate;
	}

}
