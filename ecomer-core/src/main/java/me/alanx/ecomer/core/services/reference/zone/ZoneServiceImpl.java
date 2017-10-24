package me.alanx.ecomer.core.services.reference.zone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.reference.Country;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.reference.Zone;
import me.alanx.ecomer.core.model.reference.ZoneDescription;
import me.alanx.ecomer.core.repositories.reference.zone.ZoneRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;

@Service("zoneService")
public class ZoneServiceImpl extends SalesManagerEntityServiceImpl<Long, Zone> implements
		ZoneService {
	
	private ZoneRepository zoneRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ZoneServiceImpl.class);

	@Inject
	public ZoneServiceImpl(ZoneRepository zoneRepository) {
		super(zoneRepository);
		this.zoneRepository = zoneRepository;
	}

	@Override
	public Zone getByCode(String code) {
		return zoneRepository.findByCode(code);
	}

	@Override
	public Zone addDescription(Zone zone, ZoneDescription description) throws ServiceException {
		if (zone.getDescriptions()!=null) {
				if(!zone.getDescriptions().contains(description)) {
					zone.getDescriptions().add(description);
					zone = update(zone);
				}
		} else {
			List<ZoneDescription> descriptions = new ArrayList<ZoneDescription>();
			descriptions.add(description);
			zone.setDescriptons(descriptions);
			zone = update(zone);
		}
		
		return zone;
	}
	
	@Override
	public List<Zone> getZones(Country country, Language language) throws ServiceException {
		
		List<Zone> zones = null;
		try {

			zones = zoneRepository.listByLanguageAndCountry(country.getIsoCode(), language.getId());
		
			//set names
			for(Zone zone : zones) {
				ZoneDescription description = zone.getDescriptions().get(0);
				zone.setName(description.getName());
				
			}

		} catch (Exception e) {
			LOGGER.error("getZones()", e);
		}
		return zones;
		
		
	}
	
	@Override
	public Map<String, Zone> getZones(Language language) throws ServiceException {
		
		Map<String, Zone> zones = null;
		try {
			
			zones = new HashMap<String, Zone>();
			List<Zone> zns = zoneRepository.listByLanguage(language.getId());
		
			//set names
			for(Zone zone : zns) {
				ZoneDescription description = zone.getDescriptions().get(0);
				zone.setName(description.getName());
				zones.put(zone.getCode(), zone);
				
			}

		} catch (Exception e) {
			LOGGER.error("getZones()", e);
		}
		return zones;
		
		
	}

}
