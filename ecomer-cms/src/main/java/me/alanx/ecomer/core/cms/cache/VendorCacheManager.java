package me.alanx.ecomer.core.cms.cache;

import java.io.IOException;

import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VendorCacheManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VendorCacheManager.class);
	protected final EmbeddedCacheManager manager;
	private static volatile VendorCacheManager vendorCacheManager = null;
	private String repositoryFileName = "config/infinispan_configuration.xml";
	
	private VendorCacheManager() throws IOException{
		
		manager = new DefaultCacheManager(repositoryFileName);
		
	}


	public static VendorCacheManager getInstance() {
		if(vendorCacheManager==null) {
			synchronized(VendorCacheManager.class) {
				if(vendorCacheManager == null) {
					try {
						vendorCacheManager = new VendorCacheManager();
					} catch (IOException e) {
						LOGGER.error("Cannot start manager " + e.toString());
						//throw 
					}
				}
			}
		}
		return vendorCacheManager;
	}


	public EmbeddedCacheManager getManager() {
		return manager;
	}

}
