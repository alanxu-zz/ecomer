package me.alanx.ecomer.web.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcRegistrationsAdapter;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import me.alanx.ecomer.core.services.codec.EncryptionImpl;
import me.alanx.ecomer.core.services.codec.EncryptionService;
import me.alanx.ecomer.core.services.init.DefaultDatabaseInitializer;
import me.alanx.ecomer.core.services.reference.init.DatabaseInitializer;

@Configuration
@EnableAutoConfiguration(exclude = {
        FreeMarkerAutoConfiguration.class/*, org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class*/})
@ComponentScan({"me.alanx.ecomer.core", "me.alanx.ecomer.modules", "me.alanx.ecomer.web"})
@EnableJpaRepositories("me.alanx.ecomer.core.repositories")
@EntityScan("me.alanx.ecomer.core.model")
public class EComerAdminConfiguration extends SpringBootServletInitializer{
	
	@Bean
	public EncryptionService encryptionService() {
		return new EncryptionImpl();
	}
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    @Bean
    public List<String> templates(){
    	List<String> templates = new ArrayList<>();
    	templates.add("bootstrap3");
    	return templates;
    }
    
    @Bean
    @Profile("init")
    public DatabaseInitializer dbInitializer(){
    	return new DefaultDatabaseInitializer();
    }
    
	@Bean
	public CustomEditorConfigurer customEditorConfigurer(PropertyEditorRegistrar[] propertyEditorRegistrars) {
		CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
		customEditorConfigurer.setPropertyEditorRegistrars(propertyEditorRegistrars);
		return customEditorConfigurer;
	}
	
	@Bean
	public PropertyEditorRegistrar propertyEditorRegistrar(StringTrimmerEditor stringTrimmerEditor) {
		return new PropertyEditorRegistrar(){

			@Override
			public void registerCustomEditors(PropertyEditorRegistry registry) {
				registry.registerCustomEditor(String.class, stringTrimmerEditor);
			}
			
		};
	}
	
	@Bean
	public StringTrimmerEditor stringTrimmerEditor() {
		return new StringTrimmerEditor(true);
	}
	
	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean(MessageSource messageSource) {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(messageSource);
		return localValidatorFactoryBean;
	}
	

}
