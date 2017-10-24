package me.alanx.ecomer.web.store.controller.content;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import me.alanx.ecomer.core.model.content.Content;
import me.alanx.ecomer.core.model.content.ContentDescription;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.services.content.ContentService;
import me.alanx.ecomer.web.constants.Constants;
import me.alanx.ecomer.web.dto.shop.PageInformation;
import me.alanx.ecomer.web.store.controller.ControllerConstants;

@Controller
public class ShopContentController {
	
	
	@Inject
	private ContentService contentService;

	
	@RequestMapping("/shop/pages/{friendlyUrl}.html")
	public String displayContent(@PathVariable final String friendlyUrl, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.MERCHANT_STORE);

		ContentDescription contentDescription = contentService.getBySeUrl(store, friendlyUrl);
		
		Content content = null;
		
		if(contentDescription!=null) {
			
			content = contentDescription.getContent();
			
			if(!content.isVisible()) {
				return "redirect:/shop";
			}
			
			//meta information
			PageInformation pageInformation = new PageInformation();
			pageInformation.setPageDescription(contentDescription.getMetatagDescription());
			pageInformation.setPageKeywords(contentDescription.getMetatagKeywords());
			pageInformation.setPageTitle(contentDescription.getTitle());
			pageInformation.setPageUrl(contentDescription.getName());
			
			request.setAttribute(Constants.REQUEST_PAGE_INFORMATION, pageInformation);
			
			
			
			
		}
		
		//TODO breadcrumbs
		request.setAttribute(Constants.LINK_CODE, contentDescription.getSeUrl());
		model.addAttribute("content",contentDescription);

		if(!StringUtils.isBlank(content.getProductGroup())) {
			model.addAttribute("productGroup",content.getProductGroup());
		}
		
		/** template **/
		StringBuilder template = new StringBuilder().append(ControllerConstants.Tiles.Content.content).append(".").append(store.getStoreTemplate());

		return template.toString();
		
		
	}
	
}