package me.alanx.ecomer.core.services.catalog.product.availability;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.ProductAvailability;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;

public interface ProductAvailabilityService extends
		SalesManagerEntityService<Long, ProductAvailability> {

	void saveOrUpdate(ProductAvailability availability) throws ServiceException;

}
