package me.alanx.ecomer.web.populator.manufacturer;


import java.util.Set;

import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.model.catalog.product.Manufacturer;
import me.alanx.ecomer.core.model.catalog.product.ManufacturerDescription;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;
import me.alanx.ecomer.web.dto.catalog.manufacturer.ReadableManufacturer;

public class ReadableManufacturerPopulator extends AbstractDataPopulator<Manufacturer,ReadableManufacturer>
{



	
	@Override
	public ReadableManufacturer populate(
			Manufacturer source,
			ReadableManufacturer target, MerchantStore store, Language language) throws ConversionException {
		target.setId(source.getId());
		if(source.getDescriptions()!=null && source.getDescriptions().size()>0) {
			
				Set<ManufacturerDescription> descriptions = source.getDescriptions();
				ManufacturerDescription description = null;
				for(ManufacturerDescription desc : descriptions) {
					if(desc.getLanguage().getCode().equals(language.getCode())) {
						description = desc;
						break;
					}
				}
				
				target.setOrder(source.getOrder());
				target.setId(source.getId());
				target.setCode(source.getCode());
				
				if (description != null) {
					me.alanx.ecomer.web.dto.catalog.manufacturer.ManufacturerDescription d = new me.alanx.ecomer.web.dto.catalog.manufacturer.ManufacturerDescription();
					d.setName(description.getName());
					d.setDescription(description.getDescription());
					target.setDescription(d);
				}

		}

		return target;
	}

    @Override
    protected ReadableManufacturer createTarget()
    {
        return null;
    }
}
