package me.alanx.ecomer.core.services.system;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.system.SystemConfiguration;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;

public interface SystemConfigurationService extends
		SalesManagerEntityService<Long, SystemConfiguration> {
	
	SystemConfiguration getByKey(String key) throws ServiceException;

}
