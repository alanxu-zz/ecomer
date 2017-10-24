package me.alanx.ecomer.core.services.catalog.product.file;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.DigitalProduct;
import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.content.InputContentFile;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;


public interface DigitalProductService extends SalesManagerEntityService<Long, DigitalProduct> {

	void saveOrUpdate(DigitalProduct digitalProduct) throws ServiceException;

	void addProductFile(Product product, DigitalProduct digitalProduct,
			InputContentFile inputFile) throws ServiceException;



	DigitalProduct getByProduct(MerchantStore store, Product product)
			throws ServiceException;

	
}
