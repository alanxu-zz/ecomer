package me.alanx.ecomer.core.model.catalog.product.query;

import java.util.List;

import me.alanx.ecomer.core.model.common.Criteria;

public class ProductCriteria extends Criteria {
	
	
	private String productName;
	
	private List<ProductOptionCriteria> options;
	private List<ProductAttributeCriteria> attributes;
	
	private Boolean available = null;
	
	private List<Long> categoryIds;
	private List<String> availabilities;
	private List<Long> productIds;
	
	private Long manufacturerId = null;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}


	public List<Long> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<Long> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public List<String> getAvailabilities() {
		return availabilities;
	}

	public void setAvailabilities(List<String> availabilities) {
		this.availabilities = availabilities;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public void setOptionCriterias(List<ProductOptionCriteria> attributeCriteria) {
		this.options = attributeCriteria;
	}

	public List<ProductOptionCriteria> getOptionCriterias() {
		return options;
	}

	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}

	public List<Long> getProductIds() {
		return productIds;
	}

	public void setManufacturerId(Long manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public Long getManufacturerId() {
		return manufacturerId;
	}

	/**
	 * @return the attributes
	 */
	public List<ProductAttributeCriteria> getAttributeCriterias() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributeCriterias(List<ProductAttributeCriteria> attributes) {
		this.attributes = attributes;
	}



}
