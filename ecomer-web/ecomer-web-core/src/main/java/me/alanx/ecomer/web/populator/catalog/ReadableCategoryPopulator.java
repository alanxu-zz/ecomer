package me.alanx.ecomer.web.populator.catalog;

import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.model.catalog.category.Category;
import me.alanx.ecomer.core.model.catalog.category.CategoryDescription;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;
import me.alanx.ecomer.web.dto.catalog.category.ReadableCategory;

public class ReadableCategoryPopulator extends
		AbstractDataPopulator<Category, ReadableCategory> {

	@Override
	public ReadableCategory populate(Category source, ReadableCategory target,
			MerchantStore store, Language language) throws ConversionException {
		
		
		target.setLineage(source.getLineage());
		if(source.getDescriptions()!=null && source.getDescriptions().size()>0) {
			
			CategoryDescription description = source.getDescription();
			if(source.getDescriptions().size()>1) {
				for(CategoryDescription desc : source.getDescriptions()) {
					if(desc.getLanguage().getCode().equals(language.getCode())) {
						description = desc;
						break;
					}
				}
			}
		
		
		
			if(description!=null) {
				me.alanx.ecomer.web.dto.catalog.category.CategoryDescription desc = new me.alanx.ecomer.web.dto.catalog.category.CategoryDescription();
				desc.setFriendlyUrl(description.getSeUrl());
				desc.setName(description.getName());
				desc.setDescription(description.getName());
				desc.setKeyWords(description.getMetatagKeywords());
				desc.setHighlights(description.getCategoryHighlight());
				desc.setTitle(description.getMetatagTitle());
				desc.setMetaDescription(description.getMetatagDescription());
				
				target.setDescription(desc);
			}
		
		}
		
		if(source.getParent()!=null) {
			me.alanx.ecomer.web.dto.catalog.category.Category parent = new me.alanx.ecomer.web.dto.catalog.category.Category();
			parent.setCode(source.getParent().getCode());
			parent.setId(source.getParent().getId());
			target.setParent(parent);
		}
		
		target.setCode(source.getCode());
		target.setId(source.getId());
		target.setDepth(source.getDepth());
		target.setSortOrder(source.getSortOrder());
		target.setVisible(source.isVisible());

		return target;
		
	}

	@Override
	protected ReadableCategory createTarget() {
		return null;
	}

}
