package me.alanx.ecomer.core.services.catalog.product.attribute;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOptionPrice;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;

public interface ProductOptionPriceService extends
		SalesManagerEntityService<Long, ProductOptionPrice> {

	void saveOrUpdate(ProductOptionPrice productAttribute)
			throws ServiceException;
	
	List<ProductOptionPrice> getByOptionId(MerchantStore store,
			Long id) throws ServiceException;

	List<ProductOptionPrice> getByOptionValueId(MerchantStore store,
			Long id) throws ServiceException;

	List<ProductOptionPrice> getByProductId(MerchantStore store, Product product, Language language)
			throws ServiceException;

	List<ProductOptionPrice> getByAttributeIds(MerchantStore store, Product product, List<Long> ids)
			throws ServiceException;
}
