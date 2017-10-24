package me.alanx.ecomer.core.model.catalog.product.query;

import java.util.List;

import me.alanx.ecomer.core.model.catalog.product.ProductAttribute;

public class ProductAttributeCriteria {
	
	private ProductAttribute attribute;
	private List<CriteriaValue> values;

	/**
	 * @return the attribute
	 */
	public ProductAttribute getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(ProductAttribute attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return the values
	 */
	public List<CriteriaValue> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<CriteriaValue> values) {
		this.values = values;
	}
	
	
	
}
