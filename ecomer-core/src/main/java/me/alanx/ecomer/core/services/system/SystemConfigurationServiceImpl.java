package me.alanx.ecomer.core.services.system;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.system.SystemConfiguration;
import me.alanx.ecomer.core.repositories.system.SystemConfigurationRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;

@Service("systemConfigurationService")
public class SystemConfigurationServiceImpl extends
		SalesManagerEntityServiceImpl<Long, SystemConfiguration> implements
		SystemConfigurationService {

	
	private SystemConfigurationRepository systemConfigurationReposotory;
	
	@Inject
	public SystemConfigurationServiceImpl(
			SystemConfigurationRepository systemConfigurationReposotory) {
			super(systemConfigurationReposotory);
			this.systemConfigurationReposotory = systemConfigurationReposotory;
	}
	
	public SystemConfiguration getByKey(String key) throws ServiceException {
		return systemConfigurationReposotory.findByKey(key);
	}
	



}
