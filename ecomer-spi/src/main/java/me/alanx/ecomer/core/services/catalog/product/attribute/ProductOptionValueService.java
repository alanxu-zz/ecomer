package me.alanx.ecomer.core.services.catalog.product.attribute;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOption;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOptionValue;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;

public interface ProductOptionValueService extends SalesManagerEntityService<Long, ProductOptionValue> {

	void saveOrUpdate(ProductOptionValue entity) throws ServiceException;

	List<ProductOptionValue> getByName(MerchantStore store, String name,
			Language language) throws ServiceException;


	List<ProductOptionValue> listByStore(MerchantStore store, Language language)
			throws ServiceException;

	List<ProductOptionValue> listByStoreNoReadOnly(MerchantStore store,
			Language language) throws ServiceException;

	ProductOptionValue getByCode(MerchantStore store, String optionValueCode);
	
	ProductOptionValue getById(MerchantStore store, Long optionValueId);

}
