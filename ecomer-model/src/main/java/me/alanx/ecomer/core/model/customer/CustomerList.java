package me.alanx.ecomer.core.model.customer;

import java.util.List;

import me.alanx.ecomer.core.model.common.EntityList;

public class CustomerList extends EntityList {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3108842276158069739L;
	private List<Customer> customers;
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	public List<Customer> getCustomers() {
		return customers;
	}

}
