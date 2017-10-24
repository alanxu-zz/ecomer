package me.alanx.ecomer.core.i18n;

import java.util.Locale;

public interface MessageSource {
	public String getMessage(String key, Locale locale);
	
	public String getMessage(String key, Locale locale, String defaultValue);
	
	public String getMessage(String key, String[] args, Locale locale);
}
