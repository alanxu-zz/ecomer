package me.alanx.ecomer.core.cms.content;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.content.FileContentType;
import me.alanx.ecomer.core.model.content.OutputContentFile;


/**
 * Methods to retrieve the static content from the CMS
 * @author Carl Samson
 *
 */
public interface FileGet
{

	public OutputContentFile getFile(final String merchantStoreCode, FileContentType fileContentType, String contentName) throws ServiceException;
    public List<String> getFileNames(final String merchantStoreCode,FileContentType fileContentType) throws ServiceException;
    public List<OutputContentFile> getFiles(final String merchantStoreCode, FileContentType fileContentType) throws ServiceException;
}
