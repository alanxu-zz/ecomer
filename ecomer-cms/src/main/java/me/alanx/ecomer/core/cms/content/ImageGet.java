package me.alanx.ecomer.core.cms.content;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.content.FileContentType;
import me.alanx.ecomer.core.model.content.OutputContentFile;



public interface ImageGet
{

    public List<OutputContentFile> getImages( final String merchantStoreCode, FileContentType imageContentType )
        throws ServiceException;

}
