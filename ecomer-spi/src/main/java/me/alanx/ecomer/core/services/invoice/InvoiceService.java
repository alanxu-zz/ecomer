package me.alanx.ecomer.core.services.invoice;

import java.io.ByteArrayOutputStream;

import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.Order;
import me.alanx.ecomer.core.model.reference.Language;


public interface InvoiceService {
	
	public ByteArrayOutputStream createInvoice(MerchantStore store, Order order, Language language) throws Exception;

}
