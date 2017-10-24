package me.alanx.ecomer.core.services.catalog.category;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.category.Category;
import me.alanx.ecomer.core.model.catalog.category.CategoryDescription;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;

public interface CategoryService extends SalesManagerEntityService<Long, Category> {

	List<Category> listByLineage(MerchantStore store, String lineage) throws ServiceException;
	
	List<Category> listBySeUrl(MerchantStore store, String seUrl) throws ServiceException;
	
	CategoryDescription getDescription(Category category, Language language) throws ServiceException;

	void addCategoryDescription(Category category, CategoryDescription description) throws ServiceException;

	void addChild(Category parent, Category child) throws ServiceException;

	List<Category> listByParent(Category category) throws ServiceException;
	
	List<Category> listByStoreAndParent(MerchantStore store, Category category) throws ServiceException;
	
	
	List<Category> getByName(MerchantStore store, String name, Language language) throws ServiceException;
	
	List<Category> listByStore(MerchantStore store) throws ServiceException;

	Category getByCode(MerchantStore store, String code)
			throws ServiceException;

	List<Category> listByStore(MerchantStore store, Language language)
			throws ServiceException;

	void saveOrUpdate(Category category) throws ServiceException;

	List<Category> listByDepth(MerchantStore store, int depth);

	/**
	 * Get root categories by store for a given language
	 * @param store
	 * @param depth
	 * @param language
	 * @return
	 */
	List<Category> listByDepth(MerchantStore store, int depth, Language language);

	List<Category> listByLineage(String storeCode, String lineage)
			throws ServiceException;

	Category getByCode(String storeCode, String code) throws ServiceException;

	Category getBySeUrl(MerchantStore store, String seUrl);

	List<Category> listByParent(Category category, Language language);

	Category getByLanguage(long categoryId, Language language);

	/**
	 * Returns a list by category containing the category code and the number of products
	 * 1->obj[0] = book
	 *    obj[1] = 150
	 * 2->obj[0] = novell
	 *    obj[1] = 35
	 *   ...
	 * @param store
	 * @param categoryIds
	 * @return
	 * @throws ServiceException
	 */
	List<Object[]> countProductsByCategories(MerchantStore store,
			List<Long> categoryIds) throws ServiceException;

	/**
	 * Returns a list of Category by category code for a given language
	 * @param store
	 * @param codes
	 * @param language
	 * @return
	 */
	List<Category> listByCodes(MerchantStore store, List<String> codes,
			Language language);

	/**
	 * List of Category by id
	 * @param store
	 * @param ids
	 * @param language
	 * @return
	 */
	List<Category> listByIds(MerchantStore store, List<Long> ids,
			Language language);


	
	

}
