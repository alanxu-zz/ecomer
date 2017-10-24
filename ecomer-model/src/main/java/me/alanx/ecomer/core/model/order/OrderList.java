package me.alanx.ecomer.core.model.order;

import java.util.List;

import me.alanx.ecomer.core.model.common.EntityList;

public class OrderList extends EntityList {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6645927228659963628L;
	private List<Order> orders;

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Order> getOrders() {
		return orders;
	}

}
