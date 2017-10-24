package me.alanx.ecomer.core.repositories.content;

import java.util.List;

import me.alanx.ecomer.core.model.content.ContentDescription;
import me.alanx.ecomer.core.model.content.ContentType;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;


public interface ContentRepositoryCustom {

	List<ContentDescription> listNameByType(List<ContentType> contentType,
			MerchantStore store, Language language);

	ContentDescription getBySeUrl(MerchantStore store, String seUrl);
	

}
