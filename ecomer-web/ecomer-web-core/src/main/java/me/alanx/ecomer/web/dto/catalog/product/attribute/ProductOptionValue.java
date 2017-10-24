package me.alanx.ecomer.web.dto.catalog.product.attribute;

import java.io.Serializable;

import me.alanx.ecomer.web.dto.Entity;


public class ProductOptionValue extends Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
