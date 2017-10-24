/**
 *
 */
package me.alanx.ecomer.web.store.controller;

import javax.servlet.http.HttpServletRequest;

import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.web.constants.Constants;
import me.alanx.ecomer.web.dto.paging.PaginationData;

/**
 * @author Umesh A
 *
 */
public abstract class AbstractController {


    /**
     * Method which will help to retrieving values from Session
     * based on the key being passed to the method.
     * @param key
     * @return value stored in session corresponding to the key
     */
    @SuppressWarnings( "unchecked" )
    protected <T> T getSessionAttribute(final String key, HttpServletRequest request) {
	          return (T) me.alanx.ecomer.web.utils.SessionUtil.getSessionAttribute(key, request);

	}
    
    protected void setSessionAttribute(final String key, final Object value, HttpServletRequest request) {
    	me.alanx.ecomer.web.utils.SessionUtil.setSessionAttribute(key, value, request);
	}
    
    
    protected void removeAttribute(final String key, HttpServletRequest request) {
    	me.alanx.ecomer.web.utils.SessionUtil.removeSessionAttribute(key, request);
	}
    
    protected Language getLanguage(HttpServletRequest request) {
    	return (Language)request.getAttribute(Constants.LANGUAGE);
    }

    protected PaginationData createPaginaionData( final int pageNumber, final int pageSize )
    {
        final PaginationData paginaionData = new PaginationData(pageSize,pageNumber);
       
        return paginaionData;
    }
    
    protected PaginationData calculatePaginaionData( final PaginationData paginationData, final int pageSize, final int resultCount){
        
    	int currentPage = paginationData.getCurrentPage();


    	int count = Math.min((currentPage * pageSize), resultCount);  
    	paginationData.setCountByPage(count);

    	paginationData.setTotalCount( resultCount );
        return paginationData;
    }
}
