package me.alanx.ecomer.core.cms.cache;

import javax.annotation.PreDestroy;

import org.infinispan.tree.TreeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Service;

/**
 * Used for managing images
 * @author casams1
 *
 */
@Service("cmsCacheManager")
public class StoreCacheManagerImpl extends CacheManagerImpl {
	
	
	private static final Logger log = LoggerFactory.getLogger(StoreCacheManagerImpl.class);

	private static volatile StoreCacheManagerImpl cacheManager = null;
	private final static String NAMED_CACHE = "StoreRepository";

	private StoreCacheManagerImpl() {
		super.init(NAMED_CACHE);
	}
	
	public static StoreCacheManagerImpl getInstance() {
		
		if(cacheManager==null) {
			
			synchronized(StoreCacheManagerImpl.class) {
				if(cacheManager != null) {
					cacheManager = new StoreCacheManagerImpl();
				}
			}
			
		}
		
		return cacheManager;
	}

	@SuppressWarnings("rawtypes")
	public TreeCache getTreeCache() {
		if (this.treeCache == null)
			init(NAMED_CACHE);
		return treeCache;
	}
	

	@PreDestroy
	public void destroy() {
		super.destroy();
	}
}

