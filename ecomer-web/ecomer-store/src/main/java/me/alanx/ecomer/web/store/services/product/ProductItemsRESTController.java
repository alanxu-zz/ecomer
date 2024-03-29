package me.alanx.ecomer.web.store.services.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.ProductRelationship;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.catalog.category.CategoryService;
import me.alanx.ecomer.core.services.catalog.product.PricingService;
import me.alanx.ecomer.core.services.catalog.product.ProductService;
import me.alanx.ecomer.core.services.catalog.product.attribute.ProductOptionService;
import me.alanx.ecomer.core.services.catalog.product.attribute.ProductOptionValueService;
import me.alanx.ecomer.core.services.catalog.product.manufacturer.ManufacturerService;
import me.alanx.ecomer.core.services.catalog.product.relationship.ProductRelationshipService;
import me.alanx.ecomer.core.services.catalog.product.review.ProductReviewService;
import me.alanx.ecomer.core.services.customer.CustomerService;
import me.alanx.ecomer.core.services.merchant.MerchantStoreService;
import me.alanx.ecomer.core.services.reference.language.LanguageService;
import me.alanx.ecomer.core.services.tax.TaxClassService;
import me.alanx.ecomer.web.constants.Constants;
import me.alanx.ecomer.web.dto.catalog.product.ReadableProductList;
import me.alanx.ecomer.web.store.controller.items.facade.ProductItemsFacade;
import me.alanx.ecomer.web.store.controller.product.facade.ProductFacade;
import me.alanx.ecomer.web.utils.ImageFilePath;
import me.alanx.ecomer.web.utils.RequestUtils;

/**
 * API to create, read, updat and delete a Product
 * API to create Manufacturer
 * @author Carl Samson
 *
 */
@Controller
@RequestMapping("/services")
public class ProductItemsRESTController {
	
	@Inject
	private MerchantStoreService merchantStoreService;
	
	@Inject
	private CategoryService categoryService;
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	private ProductService productService;
	
	@Inject
	private ProductFacade productFacade;
	
	@Inject
	private ProductItemsFacade productItemsFacade;
	
	@Inject
	private ProductReviewService productReviewService;
	
	@Inject
	private PricingService pricingService;

	@Inject
	private ProductOptionService productOptionService;
	
	@Inject
	private ProductOptionValueService productOptionValueService;
	
	@Inject
	private TaxClassService taxClassService;
	
	@Inject
	private ManufacturerService manufacturerService;
	
	@Inject
	private LanguageService languageService;
	
	@Inject
	@Qualifier("img")
	private ImageFilePath imageUtils;
	
	@Inject
	private RequestUtils languageUtils;
	
	@Inject
	private ProductRelationshipService productRelationshipService;
	

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductItemsRESTController.class);
	
	
	

	/**
	 * Items for manufacturer
	 * filter=BRAND&filter-value=123
	 * @param start
	 * @param max
	 * @param store
	 * @param language
	 * @param category
	 * @param filterType
	 * @param filterValue
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//@RequestMapping("/products/public/page/{start}/{max}/{store}/{language}/{category}.html/filter={filterType}/filter-value={filterValue}")
	/** fixed filter **/
	@RequestMapping("/public/products/page/{start}/{max}/{store}/{language}/manufacturer/{id}")
	@ResponseBody
	public ReadableProductList getProductItemsByManufacturer(@PathVariable int start, @PathVariable int max, @PathVariable String store, @PathVariable final String language, @PathVariable final Long id, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
		
			/**
			 * How to Spring MVC Rest web service - ajax / jquery
			 * http://codetutr.com/2013/04/09/spring-mvc-easy-rest-based-json-services-with-responsebody/
			 */
			
			MerchantStore merchantStore = (MerchantStore)request.getAttribute(Constants.MERCHANT_STORE);
			
			
			Map<String,Language> langs = languageService.getLanguagesMap();
			
			if(merchantStore!=null) {
				if(!merchantStore.getCode().equals(store)) {
					merchantStore = null; //reset for the current request
				}
			}
			
			if(merchantStore== null) {
				merchantStore = merchantStoreService.getByCode(store);
			}
			
			if(merchantStore==null) {
				LOGGER.error("Merchant store is null for code " + store);
				response.sendError(503, "Merchant store is null for code " + store);//TODO localized message
				return null;
			}
			
	
	
			Language lang = langs.get(language);
			if(lang==null) {
				lang = langs.get(Constants.DEFAULT_LANGUAGE);
			}
			

			
			ReadableProductList list = productItemsFacade.listItemsByManufacturer(merchantStore, lang, id, start, max);
			
			
			return list;
		
		} catch (Exception e) {
			LOGGER.error("Error while getting products",e);
			response.sendError(503, "An error occured while retrieving products " + e.getMessage());
		}
		
		return null;
		
	}
	
	/**
	 * Query for a product group
	 * public/products/{store code}/products/group/{id}?lang=fr|en
	 * no lang it will take session lang or default store lang
	 * @param store
	 * @param language
	 * @param groupCode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/public/{store}/products/group/{code}")
	@ResponseBody
	public ReadableProductList getProductItemsByGroup(@PathVariable String store, @PathVariable final String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {

			MerchantStore merchantStore = (MerchantStore)request.getAttribute(Constants.MERCHANT_STORE);

			
			if(merchantStore!=null) {
				if(!merchantStore.getCode().equals(store)) {
					merchantStore = null; //reset for the current request
				}
			}
			
			if(merchantStore== null) {
				merchantStore = merchantStoreService.getByCode(store);
			}
			
			if(merchantStore==null) {
				LOGGER.error("Merchant store is null for code " + store);
				response.sendError(503, "Merchant store is null for code " + store);//TODO localized message
				return null;
			}
			
	
	
			Language lang = languageUtils.getLanguage(request, merchantStore);
			
			//get product group
			List<ProductRelationship> group = productRelationshipService.getByGroup(merchantStore, code, lang);

			if(group!=null) {
				List<Long> ids = new ArrayList<Long>();
				for(ProductRelationship relationship : group) {
					Product product = relationship.getRelatedProduct();
					ids.add(product.getId());
				}
				
				ReadableProductList list = productItemsFacade.listItemsByIds(merchantStore, lang, ids, 0, 0);
				return list;
			}
			
			
		
		} catch (Exception e) {
			LOGGER.error("Error while getting products",e);
			response.sendError(503, "An error occured while retrieving products " + e.getMessage());
		}
		
		return null;
		
	}

	



}
