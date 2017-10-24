package me.alanx.ecomer.core.services.order.ordertotal;

import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.OrderSummary;
import me.alanx.ecomer.core.model.order.OrderTotal;
import me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem;

/**
 * Calculates order total based on specific
 * modules implementation
 * @author carlsamson
 *
 */
public interface OrderTotalPostProcessor {
	
	   /**
	    * Uses the OrderSummary and external tools for applying if necessary
	    * variations on the OrderTotal calculation.
	    * @param orderSummary
	    * @param shoppingCartItem
	    * @param product
	    * @param customer
	    * @param store
	    * @return
	    * @throws Exception
	    */
	   OrderTotal caculateProductPiceVariation(final OrderSummary summary, final ShoppingCartItem shoppingCartItem, final Product product, final Customer customer, final MerchantStore store) throws Exception;

}
