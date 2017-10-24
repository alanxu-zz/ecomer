package me.alanx.ecomer.core.cms.content;

import me.alanx.ecomer.core.exception.ServiceException;


public interface ImageRemove {
	
	
	public void removeImages(final String merchantStoreCode) throws ServiceException;
	
}
