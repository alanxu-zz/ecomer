package me.alanx.ecomer.core.services.reference.init;

import me.alanx.ecomer.core.exception.ServiceException;

public interface ReferenceDataInitializer {
	
	boolean isEmpty();
	
	void populate(String name) throws ServiceException;

}
