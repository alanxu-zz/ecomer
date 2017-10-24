package me.alanx.ecomer.core.services.catalog.product.price;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.price.ProductPrice;
import me.alanx.ecomer.core.model.catalog.product.price.ProductPriceDescription;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;

public interface ProductPriceService extends SalesManagerEntityService<Long, ProductPrice> {

	void addDescription(ProductPrice price, ProductPriceDescription description) throws ServiceException;

	void saveOrUpdate(ProductPrice price) throws ServiceException;
	

}
