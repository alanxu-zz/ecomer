package me.alanx.ecomer.core.cms.content;


import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.content.FileContentType;

public interface ContentImageRemove extends ImageRemove {
	
	
	
	public void removeImage(final String merchantStoreCode,final FileContentType imageContentType, final String imageName) throws ServiceException;

}
