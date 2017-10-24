/**
 * 
 */
package me.alanx.ecomer.core.cms.content;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.content.InputContentFile;


/**
 * @author Umesh Awasthi
 *
 */
public interface FilePut
{
    public void addFile(final String merchantStoreCode, InputContentFile inputStaticContentData) throws ServiceException;
    public void addFiles(final String merchantStoreCode, List<InputContentFile> inputStaticContentDataList) throws ServiceException;
}
