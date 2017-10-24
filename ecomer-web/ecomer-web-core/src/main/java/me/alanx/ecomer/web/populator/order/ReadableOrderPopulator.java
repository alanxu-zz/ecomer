package me.alanx.ecomer.web.populator.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.Order;
import me.alanx.ecomer.core.model.order.OrderTotal;
import me.alanx.ecomer.core.model.order.OrderTotalType;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;
import me.alanx.ecomer.web.dto.customer.Address;
import me.alanx.ecomer.web.dto.customer.ReadableDelivery;
import me.alanx.ecomer.web.dto.order.ReadableOrder;

public class ReadableOrderPopulator extends
		AbstractDataPopulator<Order, ReadableOrder> {

	@Override
	public ReadableOrder populate(Order source, ReadableOrder target,
			MerchantStore store, Language language) throws ConversionException {
		
		
		
		target.setId(source.getId());
		target.setDatePurchased(source.getDatePurchased());
		target.setOrderStatus(source.getStatus());
		target.setCurrency(source.getCurrency().getCode());
		target.setCurrencyModel(source.getCurrency());
		if(source.getCustomerAgreement()!=null) {
			target.setCustomerAgreed(source.getCustomerAgreement());
		}
		if(source.getConfirmedAddress()!=null) {
			target.setConfirmedAddress(source.getConfirmedAddress());
		}
		
		me.alanx.ecomer.web.dto.order.OrderTotal taxTotal = null;
		me.alanx.ecomer.web.dto.order.OrderTotal shippingTotal = null;
		
		
		if(source.getBilling()!=null) {
			Address address = new Address();
			address.setCity(source.getBilling().getCity());
			address.setAddress(source.getBilling().getAddress());
			address.setCompany(source.getBilling().getCompany());
			address.setFirstName(source.getBilling().getFirstName());
			address.setLastName(source.getBilling().getLastName());
			address.setPostalCode(source.getBilling().getPostalCode());
			address.setPhone(source.getBilling().getTelephone());
			if(source.getBilling().getCountry()!=null) {
				address.setCountry(source.getBilling().getCountry().getIsoCode());
			}
			if(source.getBilling().getZone()!=null) {
				address.setZone(source.getBilling().getZone().getCode());
			}
			
			target.setBilling(address);
		}
		
		if(source.getDelivery()!=null) {
			ReadableDelivery address = new ReadableDelivery();
			address.setCity(source.getDelivery().getCity());
			address.setAddress(source.getDelivery().getAddress());
			address.setCompany(source.getDelivery().getCompany());
			address.setFirstName(source.getDelivery().getFirstName());
			address.setLastName(source.getDelivery().getLastName());
			address.setPostalCode(source.getDelivery().getPostalCode());
			address.setPhone(source.getDelivery().getTelephone());
			if(source.getDelivery().getCountry()!=null) {
				address.setCountry(source.getDelivery().getCountry().getIsoCode());
			}
			if(source.getDelivery().getZone()!=null) {
				address.setZone(source.getDelivery().getZone().getCode());
			}
			
			target.setDelivery(address);
		}
		
		List<me.alanx.ecomer.web.dto.order.OrderTotal> totals = new ArrayList<me.alanx.ecomer.web.dto.order.OrderTotal>();
		for(OrderTotal t : source.getOrderTotal()) {
			if(t.getOrderTotalType()==null) {
				continue;
			}
			if(t.getOrderTotalType().name().equals(OrderTotalType.TOTAL.name())) {
				me.alanx.ecomer.web.dto.order.OrderTotal totalTotal = createTotal(t);
				target.setTotal(totalTotal);
				totals.add(totalTotal);
			}
			else if(t.getOrderTotalType().name().equals(OrderTotalType.TAX.name())) {
				me.alanx.ecomer.web.dto.order.OrderTotal totalTotal = createTotal(t);
				if(taxTotal==null) {
					taxTotal = totalTotal;
				} else {
					BigDecimal v = taxTotal.getValue();
					v = v.add(totalTotal.getValue());
					taxTotal.setValue(v);
				}
				target.setTax(totalTotal);
				totals.add(totalTotal);
			}
			else if(t.getOrderTotalType().name().equals(OrderTotalType.SHIPPING.name())) {
				me.alanx.ecomer.web.dto.order.OrderTotal totalTotal = createTotal(t);
				if(shippingTotal==null) {
					shippingTotal = totalTotal;
				} else {
					BigDecimal v = shippingTotal.getValue();
					v = v.add(totalTotal.getValue());
					shippingTotal.setValue(v);
				}
				target.setShipping(totalTotal);
				totals.add(totalTotal);
			}
			else if(t.getOrderTotalType().name().equals(OrderTotalType.HANDLING.name())) {
				me.alanx.ecomer.web.dto.order.OrderTotal totalTotal = createTotal(t);
				if(shippingTotal==null) {
					shippingTotal = totalTotal;
				} else {
					BigDecimal v = shippingTotal.getValue();
					v = v.add(totalTotal.getValue());
					shippingTotal.setValue(v);
				}
				target.setShipping(totalTotal);
				totals.add(totalTotal);
			}
			else if(t.getOrderTotalType().name().equals(OrderTotalType.SUBTOTAL.name())) {
				me.alanx.ecomer.web.dto.order.OrderTotal subTotal = createTotal(t);
				totals.add(subTotal);
				
			}
			else {
				me.alanx.ecomer.web.dto.order.OrderTotal otherTotal = createTotal(t);
				totals.add(otherTotal);
			}
		}
		
		target.setTotals(totals);
		
		return target;
	}
	
	private me.alanx.ecomer.web.dto.order.OrderTotal createTotal(OrderTotal t) {
		me.alanx.ecomer.web.dto.order.OrderTotal totalTotal = new me.alanx.ecomer.web.dto.order.OrderTotal();
		totalTotal.setCode(t.getOrderTotalCode());
		totalTotal.setId(t.getId());
		totalTotal.setModule(t.getModule());
		totalTotal.setOrder(t.getSortOrder());
		totalTotal.setValue(t.getValue());
		return totalTotal;
	}

	@Override
	protected ReadableOrder createTarget() {

		return null;
	}

}
