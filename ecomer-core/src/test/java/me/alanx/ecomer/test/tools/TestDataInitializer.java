package me.alanx.ecomer.test.tools;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.alanx.ecomer.core.auth.JsonAuthorizationRuleBuilder;
import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.auth.Group;
import me.alanx.ecomer.core.model.auth.Permission;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.system.MerchantConfig;
import me.alanx.ecomer.core.services.auth.GroupService;
import me.alanx.ecomer.core.services.auth.PermissionService;
import me.alanx.ecomer.core.services.merchant.MerchantStoreService;
import me.alanx.ecomer.core.services.reference.init.ReferenceDataInitializer;
import me.alanx.ecomer.core.services.system.MerchantConfigurationService;
import me.alanx.ecomer.core.services.system.SystemConfigurationService;


@Component
public class TestDataInitializer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TestDataInitializer.class);

	
	@Inject
	private MerchantConfigurationService merchantConfigurationService;

	
	@Inject
	private ReferenceDataInitializer initializationDatabase;
	
	@Inject
	private InitData initData;
	
	@Inject
	private SystemConfigurationService systemConfigurationService;
	
/*	@Inject
	private WebUserServices userDetailsService;*/

	@Inject
	protected PermissionService  permissionService;
	
	@Inject
	protected GroupService   groupService;
	
	@Inject
	protected MerchantStoreService merchantService;

    
    @Autowired
	private JsonAuthorizationRuleBuilder jsonAuthorizationRuleBuilder;
	
	@PostConstruct
	public void init() {
		
		try {
			
			if (initializationDatabase.isEmpty()) {

				//All default data to be created
				
				LOGGER.info(String.format("%s : Shopizer database is empty, populate it....", "sm-shop"));
		
				 initializationDatabase.populate(MerchantStore.DEFAULT_STORE);
				
				 MerchantStore store = merchantService.getByCode(MerchantStore.DEFAULT_STORE);
				
				 //security groups and permissions
                Set<Group> groups = jsonAuthorizationRuleBuilder.getPermissionedGroups();
                Set<Permission> permissions = jsonAuthorizationRuleBuilder.getPermissions();
                
                for (Permission p : permissions) {
                	permissionService.create(p);
                }
                
                for (Group g : groups) {
                	groupService.create(g);
                }
                
                  //userDetailsService.createDefaultAdmin();
                  MerchantConfig config = new MerchantConfig();
				  config.setAllowPurchaseItems(true);
				  config.setDisplayAddToCartOnFeaturedItems(true);
				  
				  merchantConfigurationService.saveMerchantConfig(config, store);

				  loadData();

			}
			
		} catch (Exception e) {
			LOGGER.error("Error in the init method",e);
		}
		

		
	}
	
	private void loadData() throws ServiceException {
		
		/*String loadTestData = configuration.getProperty(ApplicationConstants.POPULATE_TEST_DATA);
		boolean loadData =  !StringUtils.isBlank(loadTestData) && loadTestData.equals(SystemConstants.CONFIG_VALUE_TRUE);


		if(loadData) {

			SystemConfiguration configuration = systemConfigurationService.getByKey(ApplicationConstants.TEST_DATA_LOADED);

			if(configuration!=null) {
					if(configuration.getKey().equals(ApplicationConstants.TEST_DATA_LOADED)) {
						if(configuration.getValue().equals(SystemConstants.CONFIG_VALUE_TRUE)) {
							return;
						}
					}
			}

			initData.initInitialData();

			configuration = new SystemConfiguration();
			configuration.getAuditSection().setModifiedBy(SystemConstants.SYSTEM_USER);
			configuration.setKey(ApplicationConstants.TEST_DATA_LOADED);
			configuration.setValue(SystemConstants.CONFIG_VALUE_TRUE);
			systemConfigurationService.create(configuration);


		}*/
	}



}
