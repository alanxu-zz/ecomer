package me.alanx.ecomer.core.model.catalog.product.query;

public class ArrayCriteriaValue implements CriteriaValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8954872932411722226L;

	private final String[] values;

	public ArrayCriteriaValue(String[] values) {
		super();
		this.values = values;
	}

	/**
	 * @return the values
	 */
	public String[] getValues() {
		return values;
	}
	
	
	
	
}
