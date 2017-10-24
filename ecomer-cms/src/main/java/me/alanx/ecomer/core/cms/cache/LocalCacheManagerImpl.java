package me.alanx.ecomer.core.cms.cache;


public class LocalCacheManagerImpl {
	
	private static volatile LocalCacheManagerImpl cacheManager = null;
	
	public static LocalCacheManagerImpl getInstance() {
	        
	        if(cacheManager == null) {
	        	synchronized(LocalCacheManagerImpl.class) {
	        		if(cacheManager == null) {
	        			cacheManager = new LocalCacheManagerImpl();
	        		}
	        	}
	            
	        }
	        
	        return cacheManager;
	      
	    }
	

}
