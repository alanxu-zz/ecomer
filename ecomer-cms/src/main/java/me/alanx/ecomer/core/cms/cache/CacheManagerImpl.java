package me.alanx.ecomer.core.cms.cache;

import javax.annotation.PreDestroy;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.tree.TreeCache;
import org.infinispan.tree.TreeCacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;

public abstract class CacheManagerImpl implements CacheManager, ApplicationListener<ContextStoppedEvent> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CacheManagerImpl.class);

	@SuppressWarnings("rawtypes")
	protected TreeCache treeCache = null;
	
	private final VendorCacheManager cacheManager = VendorCacheManager.getInstance();

	@SuppressWarnings("unchecked")
	protected void init(String namedCache) {
		
		try {
				 //manager = new DefaultCacheManager(repositoryFileName);

				 @SuppressWarnings("rawtypes")
				 Cache cache = cacheManager.getManager().getCache(namedCache);
				 cache.getCacheConfiguration().invocationBatching().enabled();
		    
				 TreeCacheFactory f = new TreeCacheFactory();
		    
				 treeCache = f.createTreeCache(cache);
				 
				 cache.start();
	
		         LOGGER.debug("CMS started");



      } catch (Exception e) {
      	LOGGER.error("Error while instantiating CmsImageFileManager",e);
      } finally {
          
      }
		
		
		
	}
	
	public EmbeddedCacheManager getManager() {
		return VendorCacheManager.getInstance().getManager();
	}

	@SuppressWarnings("rawtypes")
	public TreeCache getTreeCache() {
		return treeCache;
	}
	
	@Override
	public void onApplicationEvent(ContextStoppedEvent event) {
		  
	}

	public void destroy() {
		if (cacheManager != null) {
			LOGGER.debug("Destroy CMS cache!");
			cacheManager.getManager().stop();
		}
		LOGGER.debug("Cache is null!");
	
	}
}
