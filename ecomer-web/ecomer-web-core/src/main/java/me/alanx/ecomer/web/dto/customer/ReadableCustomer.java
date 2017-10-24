package me.alanx.ecomer.web.dto.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.alanx.ecomer.web.dto.customer.attribute.ReadableCustomerAttribute;


public class ReadableCustomer extends CustomerEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ReadableCustomerAttribute> attributes = new ArrayList<ReadableCustomerAttribute>();
	public void setAttributes(List<ReadableCustomerAttribute> attributes) {
		this.attributes = attributes;
	}
	public List<ReadableCustomerAttribute> getAttributes() {
		return attributes;
	}

}
