package me.alanx.ecomer.core.repositories.system;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alanx.ecomer.core.model.system.SystemConfiguration;

public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration, Long> {


	SystemConfiguration findByKey(String key);

}
