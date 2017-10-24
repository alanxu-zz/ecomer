package me.alanx.ecomer.web.dto.admin.customer.attribute;

import java.io.Serializable;

import me.alanx.ecomer.web.dto.ShopEntity;



public class CustomerOptionValue extends ShopEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
