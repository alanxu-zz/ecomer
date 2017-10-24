package me.alanx.ecomer.core.services.reference.zone;

import java.util.List;
import java.util.Map;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.reference.Country;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.reference.Zone;
import me.alanx.ecomer.core.model.reference.ZoneDescription;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;

public interface ZoneService extends SalesManagerEntityService<Long, Zone> {
	
	Zone getByCode(String code);

	Zone addDescription(Zone zone, ZoneDescription description) throws ServiceException;

	List<Zone> getZones(Country country, Language language)
			throws ServiceException;

	Map<String, Zone> getZones(Language language) throws ServiceException;


}
