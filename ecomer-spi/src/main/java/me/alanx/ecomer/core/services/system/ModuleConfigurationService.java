package me.alanx.ecomer.core.services.system;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.system.IntegrationModule;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;

public interface ModuleConfigurationService extends
		SalesManagerEntityService<Long, IntegrationModule> {

	List<IntegrationModule> getIntegrationModules(String module);

	IntegrationModule getByCode(String moduleCode);
	
	void createOrUpdateModule(String json) throws ServiceException;
	


}
