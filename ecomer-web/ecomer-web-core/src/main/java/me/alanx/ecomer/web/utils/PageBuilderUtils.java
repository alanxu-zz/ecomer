package me.alanx.ecomer.web.utils;

import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.web.constants.Constants;

public class PageBuilderUtils {
	
	public static String build404(MerchantStore store) {
		return new StringBuilder().append("404").append(".").append(store.getStoreTemplate()).toString();
	}
	
	public static String buildHomePage(MerchantStore store) {
		return "redirect:" + Constants.SHOP_URI;
	}

}
