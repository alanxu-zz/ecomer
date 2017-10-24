package me.alanx.ecomer.web.populator.customer;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.model.customer.attribute.CustomerOptionValue;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.reference.language.LanguageService;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;
import me.alanx.ecomer.web.dto.customer.attribute.CustomerOptionValueDescription;
import me.alanx.ecomer.web.dto.customer.attribute.PersistableCustomerOptionValue;

public class PersistableCustomerOptionValuePopulator extends
		AbstractDataPopulator<PersistableCustomerOptionValue, CustomerOptionValue> {

	
	private LanguageService languageService;
	
	@Override
	public CustomerOptionValue populate(PersistableCustomerOptionValue source,
			CustomerOptionValue target, MerchantStore store, Language language)
			throws ConversionException {
		
		
		Validate.notNull(languageService, "Requires to set LanguageService");
		
		
		try {
			
			target.setCode(source.getCode());
			target.setMerchantStore(store);
			target.setSortOrder(source.getOrder());
			
			if(!CollectionUtils.isEmpty(source.getDescriptions())) {
				Set<me.alanx.ecomer.core.model.customer.attribute.CustomerOptionValueDescription> descriptions = new HashSet<me.alanx.ecomer.core.model.customer.attribute.CustomerOptionValueDescription>();
				for(CustomerOptionValueDescription desc  : source.getDescriptions()) {
					me.alanx.ecomer.core.model.customer.attribute.CustomerOptionValueDescription description = new me.alanx.ecomer.core.model.customer.attribute.CustomerOptionValueDescription();
					Language lang = languageService.getByCode(desc.getLanguage());
					if(lang==null) {
						throw new ConversionException("Language is null for code " + description.getLanguage() + " use language ISO code [en, fr ...]");
					}
					description.setLanguage(lang);
					description.setName(desc.getName());
					description.setTitle(desc.getTitle());
					description.setCustomerOptionValue(target);
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
	protected CustomerOptionValue createTarget() {
		return null;
	}

	public void setLanguageService(LanguageService languageService) {
		this.languageService = languageService;
	}

	public LanguageService getLanguageService() {
		return languageService;
	}

}
