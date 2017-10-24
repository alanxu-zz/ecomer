package me.alanx.ecomer.core.repositories.system;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import me.alanx.ecomer.core.model.system.IntegrationModule;

@CacheConfig(cacheNames = "moduleConfigs")
public interface ModuleConfigurationRepository extends JpaRepository<IntegrationModule, Long> {

	@Cacheable(key = "'NAME_' + #moduleName")
	List<IntegrationModule> findByModule(String moduleName);
	
	@Cacheable(key = "'CODE_' + #code")
	IntegrationModule findByCode(String code);
	

}
