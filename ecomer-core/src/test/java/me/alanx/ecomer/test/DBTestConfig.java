package me.alanx.ecomer.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Profile("test")
@Configuration
@ImportResource("test-db.xml")
@ComponentScan("me.alanx.ecomer.core.services")
@EnableJpaRepositories({"me.alanx.ecomer.core.repositories"})
public class DBTestConfig {}
