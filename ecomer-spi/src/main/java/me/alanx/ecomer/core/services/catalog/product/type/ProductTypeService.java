package me.alanx.ecomer.core.services.catalog.product.type;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.ProductType;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;

public interface ProductTypeService extends SalesManagerEntityService<Long, ProductType> {

	ProductType getProductType(String productTypeCode) throws ServiceException;

}
