package me.alanx.ecomer.web.dto.admin.orders;



import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Embedded;

import me.alanx.ecomer.core.model.common.Billing;
import me.alanx.ecomer.core.model.common.Delivery;
import me.alanx.ecomer.core.model.order.status.OrderStatus;


public class Order implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long Id;
	private String orderHistoryComment = "";
	
	List<OrderStatus> orderStatusList = Arrays.asList(OrderStatus.values());     
	private String datePurchased = "";
	private  me.alanx.ecomer.core.model.order.Order order;
	
	@Embedded
	private Delivery delivery = null;
	
	@Embedded
	private Billing billing = null;
	
	
	
	
	public String getDatePurchased() {
		return datePurchased;
	}

	public void setDatePurchased(String datePurchased) {
		this.datePurchased = datePurchased;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getOrderHistoryComment() {
		return orderHistoryComment;
	}

	public void setOrderHistoryComment(String orderHistoryComment) {
		this.orderHistoryComment = orderHistoryComment;
	}

	public List<OrderStatus> getOrderStatusList() {
		return orderStatusList;
	}

	public void setOrderStatusList(List<OrderStatus> orderStatusList) {
		this.orderStatusList = orderStatusList;
	}

	public me.alanx.ecomer.core.model.order.Order getOrder() {
		return order;
	}

	public void setOrder(me.alanx.ecomer.core.model.order.Order order) {
		this.order = order;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public Billing getBilling() {
		return billing;
	}

	public void setBilling(Billing billing) {
		this.billing = billing;
	}




	
}