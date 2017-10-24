package me.alanx.ecomer.web.dto.admin.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import me.alanx.ecomer.core.model.catalog.product.ManufacturerDescription;
import me.alanx.ecomer.core.model.catalog.product.ProductImage;


public class Manufacturer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4531526676134574984L;

	/**
	 * 
	 */

	//provides wrapping to the main Manufacturer entity
	private me.alanx.ecomer.core.model.catalog.product.Manufacturer manufacturer;
	
	@Valid
	private List<ManufacturerDescription> descriptions = new ArrayList<ManufacturerDescription>();
	
	private Integer order = new Integer(0);
	private MultipartFile image = null;
	@NotNull
	private String code;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private ProductImage productImage = null;
	
	
	
	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public ProductImage getProductImage() {
		return productImage;
	}

	public void setProductImage(ProductImage productImage) {
		this.productImage = productImage;
	}

	public me.alanx.ecomer.core.model.catalog.product.Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(
			me.alanx.ecomer.core.model.catalog.product.Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public List<ManufacturerDescription> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<ManufacturerDescription> descriptions) {
		this.descriptions = descriptions;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
	
	

}
