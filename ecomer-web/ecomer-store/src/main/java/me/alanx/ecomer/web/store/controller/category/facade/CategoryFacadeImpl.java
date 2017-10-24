package me.alanx.ecomer.web.store.controller.category.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.category.Category;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.catalog.category.CategoryService;
import me.alanx.ecomer.core.services.reference.language.LanguageService;
import me.alanx.ecomer.web.dto.catalog.category.PersistableCategory;
import me.alanx.ecomer.web.dto.catalog.category.ReadableCategory;
import me.alanx.ecomer.web.populator.catalog.PersistableCategoryPopulator;
import me.alanx.ecomer.web.populator.catalog.ReadableCategoryPopulator;


@Service( value = "categoryFacade" )
public class CategoryFacadeImpl implements CategoryFacade {
	
	@Inject
	private CategoryService categoryService;
	
	@Inject
	private LanguageService languageService;

	@Override
	public List<ReadableCategory> getCategoryHierarchy(MerchantStore store,
			int depth, Language language) throws Exception {
		
		List<Category> categories = categoryService.listByDepth(store, depth, language);
		List<ReadableCategory> returnValues = new ArrayList<ReadableCategory>();
		
		Map<Long, ReadableCategory> categoryMap = new ConcurrentHashMap<Long, ReadableCategory>();
		
		ReadableCategoryPopulator categoryPopulator = new ReadableCategoryPopulator();
		
		for(Category category : categories) {
			
			if(category.isVisible()) {
				ReadableCategory readableCategory = new ReadableCategory();
				categoryPopulator.populate(category, readableCategory, store, language);
				
				returnValues.add(readableCategory);
				categoryMap.put(category.getId(), readableCategory);
			}
		}
		
		for(ReadableCategory category : returnValues) {
			
			if(category.isVisible()) {
				if(category.getParent()!=null) {
				    ReadableCategory parentCategory = categoryMap.get(category.getParent().getId());
					if(parentCategory!=null) {
						parentCategory.getChildren().add(category);
					}
				}
			}
		}
		
		returnValues = new ArrayList<ReadableCategory>();
		for(Object obj : categoryMap.values()) {
			
			ReadableCategory readableCategory = (ReadableCategory)obj;
			if(readableCategory.getDepth()==0) {//only from root
				returnValues.add(readableCategory);
			}
		}
		
        Collections.sort(returnValues, new Comparator<ReadableCategory>() {
            @Override
            public int compare(final ReadableCategory firstCategory, final ReadableCategory secondCategory) {
                return firstCategory.getSortOrder() - secondCategory.getSortOrder();
            }
         } );
		
		return returnValues;
	}

	@Override
	public void saveCategory(MerchantStore store, PersistableCategory category)
			throws Exception {
		
		PersistableCategoryPopulator populator = new PersistableCategoryPopulator();
		populator.setCategoryService(categoryService);
		populator.setLanguageService(languageService);
		
		Category dbCategory = populator.populate(category, new Category(), store, store.getDefaultLanguage());
		
		this.saveCategory(store, dbCategory, null);
		
		
	}
	
	private void saveCategory(MerchantStore store, Category c, Category parent) throws ServiceException {
		
		
		/**
		c.children1
		
		  			children1.children1
		  			children1.children2
		  
          								children1.children2.children1			
		
		**/
		
		/** set lineage **/
		if(parent!=null) {
			c.setParent(c);
			
			String lineage = parent.getLineage();
			int depth = parent.getDepth();

			c.setDepth(depth+1);
			c.setLineage(new StringBuilder().append(lineage).append(parent.getId()).append("/").toString());
			
		}
		
		c.setMerchantStore(store);
		
		//remove children
		List<Category> children = c.getCategories();
		c.setCategories(null);
		
		/** set parent **/
		if(parent!=null) {
			c.setParent(parent);
		}
		
		categoryService.saveOrUpdate(c);
		
		
		if(!CollectionUtils.isEmpty(children)) {
			parent = c;
			for(Category sub : children) {
				
				this.saveCategory(store, sub, parent);
				
			}
		}
	}

}
