package me.alanx.ecomer.core.cms.content;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.content.FileContentType;
import me.alanx.ecomer.core.model.content.OutputContentFile;

public interface ContentImageGet extends ImageGet {
	
	public OutputContentFile getImage(final String merchantStoreCode, String imageName,FileContentType imageContentType) throws ServiceException;
	public List<String> getImageNames(final String merchantStoreCode, FileContentType imageContentType) throws ServiceException;

}
