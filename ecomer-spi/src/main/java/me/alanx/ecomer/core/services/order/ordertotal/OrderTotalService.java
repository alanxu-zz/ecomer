package me.alanx.ecomer.core.services.order.ordertotal;

import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.OrderSummary;
import me.alanx.ecomer.core.model.order.OrderTotalVariation;
import me.alanx.ecomer.core.model.reference.Language;

/**
 * Additional dynamic order total calculation
 * from the rules engine and other modules
 * @author carlsamson
 *
 */
public interface OrderTotalService {
	
	OrderTotalVariation findOrderTotalVariation(final OrderSummary summary, final Customer customer, final MerchantStore store, final Language language) throws Exception;

}
