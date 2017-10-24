package me.alanx.ecomer.core.services.catalog.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOptionPrice;
import me.alanx.ecomer.core.model.catalog.product.price.FinalPrice;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Currency;
import me.alanx.ecomer.core.utils.ProductPriceUtils;

/**
 * Contains all the logic required to calculate product price
 * @author Carl Samson
 *
 */
@Service("pricingService")
public class PricingServiceImpl implements PricingService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PricingServiceImpl.class);
	
	
	@Override
	public FinalPrice calculateProductPrice(Product product) throws ServiceException {
		return ProductPriceUtils.getFinalPrice(product);
	}
	
	@Override
	public FinalPrice calculateProductPrice(Product product, Customer customer) throws ServiceException {
		/** TODO add rules for price calculation **/
		return ProductPriceUtils.getFinalPrice(product);
	}
	
	@Override
	public FinalPrice calculateProductPrice(Product product, List<ProductOptionPrice> attributes) throws ServiceException {
		return ProductPriceUtils.getFinalProductPrice(product, attributes);
	}
	
	@Override
	public FinalPrice calculateProductPrice(Product product, List<ProductOptionPrice> attributes, Customer customer) throws ServiceException {
		/** TODO add rules for price calculation **/
		return ProductPriceUtils.getFinalProductPrice(product, attributes);
	}

	@Override
	public String getDisplayAmount(BigDecimal amount, MerchantStore store) throws ServiceException {
		try {
			String price= ProductPriceUtils.getStoreFormatedAmountWithCurrency(store,amount);
			return price;
		} catch (Exception e) {
			LOGGER.error("An error occured when trying to format an amount " + amount.toString());
			throw new ServiceException(e);
		}
	}
	
	@Override
	public String getDisplayAmount(BigDecimal amount, Locale locale,
			Currency currency, MerchantStore store) throws ServiceException {
		try {
			String price= ProductPriceUtils.getFormatedAmountWithCurrency(locale, currency, amount);
			return price;
		} catch (Exception e) {
			LOGGER.error("An error occured when trying to format an amunt " + amount.toString() + " using locale " + locale.toString() + " and currency " + currency.toString());
			throw new ServiceException(e);
		}
	}

	@Override
	public String getStringAmount(BigDecimal amount, MerchantStore store)
			throws ServiceException {
		try {
			String price = ProductPriceUtils.getAdminFormatedAmount(store, amount);
			return price;
		} catch (Exception e) {
			LOGGER.error("An error occured when trying to format an amount " + amount.toString());
			throw new ServiceException(e);
		}
	}


	
}
