package me.alanx.ecomer.core.model.catalog.product.query;

public class ScopeCriteriaValue implements CriteriaValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4586657911184572439L;
	
	private final Long minValue;
	private final Long maxValue;
	public ScopeCriteriaValue(Long minValue, Long maxValue) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	/**
	 * @return the minValue
	 */
	public Long getMinValue() {
		return minValue;
	}
	/**
	 * @return the maxValue
	 */
	public Long getMaxValue() {
		return maxValue;
	}
	
	

}
