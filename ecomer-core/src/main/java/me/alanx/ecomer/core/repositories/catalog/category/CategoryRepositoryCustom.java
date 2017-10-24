package me.alanx.ecomer.core.repositories.catalog.category;

import java.util.List;

import me.alanx.ecomer.core.model.catalog.category.Category;
import me.alanx.ecomer.core.model.merchant.MerchantStore;

public interface CategoryRepositoryCustom {

	List<Object[]> countProductsByCategories(MerchantStore store,
			List<Long> categoryIds);

	List<Category> listByStoreAndParent(MerchantStore store, Category category);

}
