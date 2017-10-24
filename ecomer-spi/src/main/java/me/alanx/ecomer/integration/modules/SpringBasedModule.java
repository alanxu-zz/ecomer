package me.alanx.ecomer.integration.modules;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.Ordered;

public abstract class SpringBasedModule implements Module, BeanNameAware, Ordered{

	private final String moduleName;
	private String beanName;
	
	public SpringBasedModule(String moduleName) {
		this.moduleName = moduleName;
	}
	
	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	@Override
	public String getName() {
		if (this.moduleName != null && this.moduleName.trim().length() > 0) {
			return this.moduleName;
		}
		return this.beanName;
	}

}
