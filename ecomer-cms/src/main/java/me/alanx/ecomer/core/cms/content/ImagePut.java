package me.alanx.ecomer.core.cms.content;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.content.InputContentFile;

public interface ImagePut {
	
	
	public void addImage(final String merchantStoreCode, InputContentFile image) throws ServiceException;
	public void addImages(final String merchantStoreCode, List<InputContentFile> imagesList) throws ServiceException;

}
