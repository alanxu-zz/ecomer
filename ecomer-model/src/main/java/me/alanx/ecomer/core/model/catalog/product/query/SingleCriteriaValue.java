package me.alanx.ecomer.core.model.catalog.product.query;

public class SingleCriteriaValue implements CriteriaValue {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3824949984742823660L;
	
	private final String value;

	public SingleCriteriaValue(String value) {
		super();
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	
	
}
