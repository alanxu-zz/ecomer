/**
 * 
 */
package me.alanx.ecomer.core.cms.content;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.content.FileContentType;


/**
 * @author Umesh Awasthi
 *
 */
public interface FileRemove
{
    public void removeFile(String merchantStoreCode, FileContentType staticContentType, String fileName) throws ServiceException;
    public void removeFiles(String merchantStoreCode) throws ServiceException;

}
