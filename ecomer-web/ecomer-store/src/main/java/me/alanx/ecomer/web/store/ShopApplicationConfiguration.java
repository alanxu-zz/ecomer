package me.alanx.ecomer.web.store;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import me.alanx.ecomer.core.services.codec.EncryptionImpl;
import me.alanx.ecomer.core.services.codec.EncryptionService;
import me.alanx.ecomer.core.services.init.DefaultDatabaseInitializer;
import me.alanx.ecomer.core.services.reference.init.DatabaseInitializer;
import me.alanx.ecomer.web.store.services.init.DefaultStoreDataInitializer;


@Configuration
@ComponentScan({"me.alanx.ecomer.core", "me.alanx.ecomer.modules", "me.alanx.ecomer.web"})
@EnableJpaRepositories("me.alanx.ecomer.core.repositories")
@EntityScan("me.alanx.ecomer.core.model")
public class ShopApplicationConfiguration extends SpringBootServletInitializer{

    /**
     * Configure TilesConfigurer.
     */
    @Bean
    public TilesConfigurer tilesConfigurer(){
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[] {"/WEB-INF/tiles/tiles-shop.xml"});
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }
 
    /**
     * Configure ViewResolvers to deliver preferred views.
     */

    @Bean
    public TilesViewResolver tilesViewResolver() {
        final TilesViewResolver resolver = new TilesViewResolver();
        resolver.setViewClass(TilesView.class);
        return resolver;
    }
    
	/**
	 * Configure EncryptionService (EncryptionImpl is in spi package so cannot benefit componentScan feature of Spring)
	 */
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
    	return new DefaultStoreDataInitializer();
    }

}
