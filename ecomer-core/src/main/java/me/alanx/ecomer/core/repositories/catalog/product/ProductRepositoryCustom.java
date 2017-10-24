package me.alanx.ecomer.core.repositories.catalog.product;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.query.ProductCriteria;
import me.alanx.ecomer.core.model.catalog.product.query.ProductList;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.tax.taxclass.TaxClass;

public interface ProductRepositoryCustom {
	
	
	
	

		ProductList listByStore(MerchantStore store, Language language,
			ProductCriteria criteria);
		
		 Product getByFriendlyUrl(MerchantStore store,String seUrl, Locale locale);

		List<Product> getProductsListByCategories(@SuppressWarnings("rawtypes") Set categoryIds);

		List<Product> getProductsListByCategories(Set<Long> categoryIds,
				Language language);

		List<Product> listByTaxClass(TaxClass taxClass);

		List<Product> listByStore(MerchantStore store);

		Product getProductForLocale(long productId, Language language,
				Locale locale);

		Product getById(Long productId);

		Product getByCode(String productCode, Language language);

		List<Product> getProductsForLocale(MerchantStore store,
				Set<Long> categoryIds, Language language, Locale locale);

}
