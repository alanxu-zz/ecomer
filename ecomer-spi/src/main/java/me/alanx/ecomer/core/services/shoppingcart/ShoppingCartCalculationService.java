/**
 *
 */
package me.alanx.ecomer.core.services.shoppingcart;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.OrderTotalSummary;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.shoppingcart.ShoppingCart;

/**
 * Interface declaring various methods used to calculate {@link ShoppingCart}
 * object details.
 * 
 * @author Umesh Awasthi
 * @since 1.2
 *
 */
public interface ShoppingCartCalculationService {
	/**
	 * Method which will be used to calculate price for each line items as well
	 * Total and Sub-total for {@link ShoppingCart}.
	 * 
	 * @param cartModel
	 *            ShoopingCart mode representing underlying DB object
	 * @param customer
	 * @param store
	 * @param language
	 * @throws ServiceException
	 */
	public OrderTotalSummary calculate(final ShoppingCart cartModel, final Customer customer, final MerchantStore store,
			final Language language) throws ServiceException;

	/**
	 * Method which will be used to calculate price for each line items as well
	 * Total and Sub-total for {@link ShoppingCart}.
	 * 
	 * @param cartModel
	 *            ShoopingCart mode representing underlying DB object
	 * @param store
	 * @param language
	 * @throws ServiceException
	 */
	public OrderTotalSummary calculate(final ShoppingCart cartModel, final MerchantStore store, final Language language)
			throws ServiceException;
}
