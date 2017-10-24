package me.alanx.ecomer.integration.modules;

public interface ModuleManager<M extends Module> {
	
	final static String DEFAULT_MODULE_LOADING_PACKAGE = "me.alanx.ecomer.integration.modules";
	
	M findOne(String name);
	
	M[] findAll(String name);
	
	M[] findAll();
	
}
