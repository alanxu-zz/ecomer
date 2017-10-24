package me.alanx.ecomer.web.store.controller.search.facade;

import me.alanx.ecomer.core.model.merchant.MerchantStore;

/**
 * Different services for searching and indexing data
 * @author c.samson
 *
 */
public interface SearchFacade {
	

	
	public void indexAllData(MerchantStore store) throws Exception;

}
