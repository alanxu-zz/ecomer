package me.alanx.ecomer.core.services.catalog.product.attribute;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOption;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;

public interface ProductOptionService extends SalesManagerEntityService<Long, ProductOption> {

	List<ProductOption> listByStore(MerchantStore store, Language language)
			throws ServiceException;


	List<ProductOption> getByName(MerchantStore store, String name,
			Language language) throws ServiceException;

	void saveOrUpdate(ProductOption entity) throws ServiceException;


	List<ProductOption> listReadOnly(MerchantStore store, Language language)
			throws ServiceException;


	ProductOption getByCode(MerchantStore store, String optionCode);
	
	ProductOption getById(MerchantStore store, Long optionId);
	



}
