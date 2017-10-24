package me.alanx.ecomer.web.utils;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.alanx.ecomer.core.constants.Constants;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;



public class LocaleUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LocaleUtils.class);
	
	public static Locale getLocale(Language language) {
		
		return new Locale(language.getCode());
		
	}
	
	/**
	 * Creates a Locale object for currency format only with country code
	 * This method ignoes the language
	 * @param store
	 * @return
	 */
	public static Locale getLocale(MerchantStore store) {
		
		Locale defaultLocale = Constants.DEFAULT_LOCALE;
		Locale[] locales = Locale.getAvailableLocales();
		for(int i = 0; i< locales.length; i++) {
			Locale l = locales[i];
			try {
				if(l.getISO3Country().equals(store.getCurrency().getCode())) {
					defaultLocale = l;
					break;
				}
			} catch(Exception e) {
				LOGGER.error("An error occured while getting ISO code for locale " + l.toString());
			}
		}
		
		return defaultLocale;
		
	}
	

}
