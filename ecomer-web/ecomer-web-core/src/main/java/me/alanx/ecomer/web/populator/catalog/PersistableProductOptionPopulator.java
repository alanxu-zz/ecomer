package me.alanx.ecomer.web.populator.catalog;


import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOption;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.reference.language.LanguageService;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;
import me.alanx.ecomer.web.dto.catalog.product.attribute.PersistableProductOption;
import me.alanx.ecomer.web.dto.catalog.product.attribute.ProductOptionDescription;




public class PersistableProductOptionPopulator extends
		AbstractDataPopulator<PersistableProductOption, ProductOption> {
	
	private LanguageService languageService;

	public LanguageService getLanguageService() {
		return languageService;
	}

	public void setLanguageService(LanguageService languageService) {
		this.languageService = languageService;
	}

	@Override
	public ProductOption populate(PersistableProductOption source,
			ProductOption target, MerchantStore store, Language language)
			throws ConversionException {
		Validate.notNull(languageService, "Requires to set LanguageService");
		
		
		try {
			

			target.setMerchantStore(store);
			target.setProductOptionSortOrder(source.getOrder());
			target.setCode(source.getCode());
			
			if(!CollectionUtils.isEmpty(source.getDescriptions())) {
				Set<me.alanx.ecomer.core.model.catalog.product.option.ProductOptionDescription> descriptions = new HashSet<me.alanx.ecomer.core.model.catalog.product.option.ProductOptionDescription>();
				for(ProductOptionDescription desc  : source.getDescriptions()) {
					me.alanx.ecomer.core.model.catalog.product.option.ProductOptionDescription description = new me.alanx.ecomer.core.model.catalog.product.option.ProductOptionDescription();
					Language lang = languageService.getByCode(desc.getLanguage());
					if(lang==null) {
						throw new ConversionException("Language is null for code " + description.getLanguage() + " use language ISO code [en, fr ...]");
					}
					description.setLanguage(lang);
					description.setName(desc.getName());
					description.setTitle(desc.getTitle());
					description.setProductOption(target);
					descriptions.add(description);
				}
				target.setDescriptions(descriptions);
			}
		
		} catch (Exception e) {
			throw new ConversionException(e);
		}
		
		
		return target;
	}

	@Override
	protected ProductOption createTarget() {
		return null;
	}

}
