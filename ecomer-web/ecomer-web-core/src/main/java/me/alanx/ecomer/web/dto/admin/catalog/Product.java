package me.alanx.ecomer.web.dto.admin.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import me.alanx.ecomer.core.model.catalog.product.ProductAvailability;
import me.alanx.ecomer.core.model.catalog.product.ProductDescription;
import me.alanx.ecomer.core.model.catalog.product.ProductImage;

public class Product implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4531526676134574984L;

	/**
	 * 
	 */

	//provides wrapping to the main product entity
	@Valid
	private me.alanx.ecomer.core.model.catalog.product.Product product;
	
	@Valid
	private List<ProductDescription> descriptions = new ArrayList<ProductDescription>();
	
	@Valid
	private ProductAvailability availability = null;
	
	@Valid
	private me.alanx.ecomer.core.model.catalog.product.price.ProductPrice price = null;
	
	private MultipartFile image = null;
	
	private ProductImage productImage = null;
	
	@NotEmpty
	private String productPrice = "0";
	
	private String dateAvailable;

	private ProductDescription description = null;
	
	public String getDateAvailable() {
		return dateAvailable;
	}
	public void setDateAvailable(String dateAvailable) {
		this.dateAvailable = dateAvailable;
	}
	public me.alanx.ecomer.core.model.catalog.product.Product getProduct() {
		return product;
	}
	public void setProduct(me.alanx.ecomer.core.model.catalog.product.Product product) {
		this.product = product;
	}
	
	public List<ProductDescription> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(List<ProductDescription> descriptions) {
		this.descriptions = descriptions;
	}
	public void setAvailability(ProductAvailability availability) {
		this.availability = availability;
	}
	public ProductAvailability getAvailability() {
		return availability;
	}
	public void setPrice(me.alanx.ecomer.core.model.catalog.product.price.ProductPrice price) {
		this.price = price;
	}
	public me.alanx.ecomer.core.model.catalog.product.price.ProductPrice getPrice() {
		return price;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductImage(ProductImage productImage) {
		this.productImage = productImage;
	}
	public ProductImage getProductImage() {
		return productImage;
	}
	public void setDescription(ProductDescription description) {
		this.description = description;
	}
	public ProductDescription getDescription() {
		return description;
	}
	





}
