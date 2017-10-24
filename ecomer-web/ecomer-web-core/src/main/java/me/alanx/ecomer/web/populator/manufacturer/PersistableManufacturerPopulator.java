
package me.alanx.ecomer.web.populator.manufacturer;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.model.catalog.product.Manufacturer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.reference.language.LanguageService;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;
import me.alanx.ecomer.web.dto.catalog.manufacturer.ManufacturerDescription;
import me.alanx.ecomer.web.dto.catalog.manufacturer.PersistableManufacturer;


/**
 * @author Carl Samson
 *
 */


public class PersistableManufacturerPopulator extends AbstractDataPopulator<PersistableManufacturer, Manufacturer>
{
	
	
	private LanguageService languageService;

	@Override
	public Manufacturer populate(PersistableManufacturer source,
			Manufacturer target, MerchantStore store, Language language)
			throws ConversionException {
		
		Validate.notNull(languageService, "Requires to set LanguageService");
		
		try {
			
			target.setMerchantStore(store);
			target.setCode(source.getCode());
			

			if(!CollectionUtils.isEmpty(source.getDescriptions())) {
				Set<me.alanx.ecomer.core.model.catalog.product.ManufacturerDescription> descriptions = new HashSet<me.alanx.ecomer.core.model.catalog.product.ManufacturerDescription>();
				for(ManufacturerDescription description : source.getDescriptions()) {
					me.alanx.ecomer.core.model.catalog.product.ManufacturerDescription desc = new me.alanx.ecomer.core.model.catalog.product.ManufacturerDescription();
					desc.setManufacturer(target);
					desc.setDescription(description.getDescription());
					desc.setName(description.getName());
					Language lang = languageService.getByCode(description.getLanguage());
					if(lang==null) {
						throw new ConversionException("Language is null for code " + description.getLanguage() + " use language ISO code [en, fr ...]");
					}
					desc.setLanguage(lang);
					descriptions.add(desc);
				}
				target.setDescriptions(descriptions);
			}
		
		} catch (Exception e) {
			throw new ConversionException(e);
		}
	
		
		return target;
	}

	@Override
	protected Manufacturer createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLanguageService(LanguageService languageService) {
		this.languageService = languageService;
	}

	public LanguageService getLanguageService() {
		return languageService;
	}


}
