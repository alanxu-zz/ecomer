package me.alanx.ecomer.core.services.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.IntegrationException;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.Order;
import me.alanx.ecomer.core.model.payments.Payment;
import me.alanx.ecomer.core.model.payments.PaymentType;
import me.alanx.ecomer.core.model.payments.Transaction;
import me.alanx.ecomer.core.model.payments.TransactionType;
import me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem;
import me.alanx.ecomer.core.model.system.IntegrationConfiguration;
import me.alanx.ecomer.core.model.system.IntegrationModule;
import me.alanx.ecomer.integration.modules.ModularizedPaymentService;

@Service
public class MoneyOrderPayment extends ModularizedPaymentService {

	public MoneyOrderPayment() {
		super(PaymentType.MONEYORDER.getPaymentName());
	}

	@Override
	public void validateModuleConfiguration(
			IntegrationConfiguration integrationConfiguration,
			MerchantStore store) throws IntegrationException {
		
		List<String> errorFields = null;
		
		
		Map<String,String> keys = integrationConfiguration.getIntegrationKeys();
		
		//validate integrationKeys['address']
		if(keys==null || StringUtils.isBlank(keys.get("address"))) {
			errorFields = new ArrayList<String>();
			errorFields.add("address");
		}
		
		if(errorFields!=null) {
			IntegrationException ex = new IntegrationException(IntegrationException.ERROR_VALIDATION_SAVE);
			ex.setErrorFields(errorFields);
			throw ex;
			
		}
		
		
		
			return;

	}

	@Override
	public Transaction initTransaction(MerchantStore store, Customer customer,
			BigDecimal amount, Payment payment,
			IntegrationConfiguration configuration, IntegrationModule module)
			throws IntegrationException {
		//NOT REQUIRED
		return null;
	}

	@Override
	public Transaction authorize(MerchantStore store, Customer customer,
			List<ShoppingCartItem> items, BigDecimal amount, Payment payment,
			IntegrationConfiguration configuration, IntegrationModule module)
			throws IntegrationException {
		//NOT REQUIRED
		return null;
	}

/*	@Override
	public Transaction capture(MerchantStore store, Customer customer,
			List<ShoppingCartItem> items, BigDecimal amount, Payment payment, Transaction transaction,
			IntegrationConfiguration configuration, IntegrationModule module)
			throws IntegrationException {
		//NOT REQUIRED
		return null;
	}*/

	@Override
	public Transaction authorizeAndCapture(MerchantStore store, Customer customer,
			List<ShoppingCartItem> items, BigDecimal amount, Payment payment,
			IntegrationConfiguration configuration, IntegrationModule module)
			throws IntegrationException {
		
		
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setTransactionDate(new Date());
		transaction.setTransactionType(TransactionType.AUTHORIZECAPTURE);
		transaction.setPaymentType(PaymentType.MONEYORDER);

		
		return transaction;
		
		
		
	}

	@Override
	public Transaction refund(boolean partial, MerchantStore store, Transaction transaction,
			Order order, BigDecimal amount, 
			IntegrationConfiguration configuration, IntegrationModule module)
			throws IntegrationException {
		throw new IntegrationException("Transaction not supported");
	}

	@Override
	public Transaction capture(MerchantStore store, Customer customer,
			Order order, Transaction capturableTransaction,
			IntegrationConfiguration configuration, IntegrationModule module)
			throws IntegrationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getOrder() {
		return 1;
	}

}
