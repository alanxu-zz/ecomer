package me.alanx.ecomer.web.store.controller.category.facade;

import java.util.List;

import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.web.dto.catalog.category.PersistableCategory;
import me.alanx.ecomer.web.dto.catalog.category.ReadableCategory;

public interface CategoryFacade {
	
	/**
	 * Returns a list of ReadableCategory ordered and built according to a given depth
	 * @param store
	 * @param depth
	 * @param language
	 * @return
	 * @throws Exception
	 */
	List<ReadableCategory> getCategoryHierarchy(MerchantStore store, int depth, Language language) throws Exception;
	
	void saveCategory(MerchantStore store, PersistableCategory category) throws Exception;

}
