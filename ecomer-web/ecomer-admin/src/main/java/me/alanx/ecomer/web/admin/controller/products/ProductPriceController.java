package me.alanx.ecomer.web.admin.controller.products;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.ProductAvailability;
import me.alanx.ecomer.core.model.catalog.product.price.ProductPrice;
import me.alanx.ecomer.core.model.catalog.product.price.ProductPriceDescription;
import me.alanx.ecomer.core.model.catalog.product.price.ProductPriceType;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.catalog.product.ProductService;
import me.alanx.ecomer.core.services.catalog.product.price.ProductPriceService;
import me.alanx.ecomer.core.utils.ProductPriceUtils;
import me.alanx.ecomer.web.admin.controller.ControllerConstants;
import me.alanx.ecomer.web.ajax.AjaxPageableResponse;
import me.alanx.ecomer.web.ajax.AjaxResponse;
import me.alanx.ecomer.web.constants.Constants;
import me.alanx.ecomer.web.dto.admin.web.Menu;
import me.alanx.ecomer.web.utils.DateUtil;
import me.alanx.ecomer.web.utils.LabelUtils;

@Controller
public class ProductPriceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductPriceController.class);
	
	@Inject
	private ProductService productService;
	
	@Inject
	private ProductPriceService productPriceService;
	
	@Inject
	LabelUtils messages;
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/products/prices.html", method=RequestMethod.GET)
	public String getProductPrices(@RequestParam("id") long productId,Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		setMenu(model,request);
		
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);

		//get the product and validate it belongs to the current merchant
		Product product = productService.getById(productId);
		
		if(product==null) {
			return "redirect:/admin/products/products.html";
		}
		
		if(product.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
			return "redirect:/admin/products/products.html";
		}
		
		ProductAvailability productAvailability = null;
		for(ProductAvailability availability : product.getAvailabilities()) {
			if(availability.getRegion().equals(me.alanx.ecomer.core.constants.Constants.ALL_REGIONS)) {
				productAvailability = availability;
			}
		}

		model.addAttribute("product",product);
		model.addAttribute("availability",productAvailability);

		return ControllerConstants.Tiles.Product.productPrices;
		
	}
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/products/prices/paging.html", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> pagePrices(HttpServletRequest request, HttpServletResponse response) {

		String sProductId = request.getParameter("productId");
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		Language language = (Language)request.getAttribute("LANGUAGE");
		
		
		AjaxResponse resp = new AjaxResponse();
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		Long productId;
		Product product = null;
		
		try {
			productId = Long.parseLong(sProductId);
		} catch (Exception e) {
			resp.setStatus(AjaxPageableResponse.RESPONSE_STATUS_FAIURE);
			resp.setErrorString("Product id is not valid");
			String returnString = resp.toJSONString();
			return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
		}

		
		try {

			product = productService.getById(productId);

			
			if(product==null) {
				resp.setStatus(AjaxPageableResponse.RESPONSE_STATUS_FAIURE);
				resp.setErrorString("Product id is not valid");
				String returnString = resp.toJSONString();
				return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
			}
			
			if(product.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
				resp.setStatus(AjaxPageableResponse.RESPONSE_STATUS_FAIURE);
				resp.setErrorString("Product id is not valid");
				String returnString = resp.toJSONString();
				return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
			}
			
			ProductAvailability defaultAvailability = null;
			
			Set<ProductAvailability> availabilities = product.getAvailabilities();

			//get default availability
			for(ProductAvailability availability : availabilities) {
				if(availability.getRegion().equals(me.alanx.ecomer.core.constants.Constants.ALL_REGIONS)) {
					defaultAvailability = availability;
					break;
				}
			}
			
			if(defaultAvailability==null) {
				resp.setStatus(AjaxPageableResponse.RESPONSE_STATUS_FAIURE);
				resp.setErrorString("Product id is not valid");
				String returnString = resp.toJSONString();
				return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
			}
			
			Set<ProductPrice> prices = defaultAvailability.getPrices();
			
			
			for(ProductPrice price : prices) {
				Map entry = new HashMap();
				entry.put("priceId", price.getId());
				
				
				String priceName = "";
				Set<ProductPriceDescription> descriptions = price.getDescriptions();
				if(descriptions!=null) {
					for(ProductPriceDescription description : descriptions) {
						if(description.getLanguage().getCode().equals(language.getCode())) {
							priceName = description.getName(); 
						}
					}
				}
				

				entry.put("name", priceName);
				entry.put("price", ProductPriceUtils.getAdminFormatedAmountWithCurrency(store,price.getProductPriceAmount()));
				entry.put("specialPrice", ProductPriceUtils.getAdminFormatedAmountWithCurrency(store,price.getProductPriceSpecialAmount()));
				
				String discount = "";
				if(ProductPriceUtils.hasDiscount(price)) {
					discount = ProductPriceUtils.getAdminFormatedAmountWithCurrency(store,price.getProductPriceAmount());
				}
				entry.put("special", discount);
				
				resp.addDataEntry(entry);
			}

			resp.setStatus(AjaxPageableResponse.RESPONSE_STATUS_SUCCESS);
		
		} catch (Exception e) {
			LOGGER.error("Error while paging products", e);
			resp.setStatus(AjaxPageableResponse.RESPONSE_STATUS_FAIURE);
			resp.setErrorMessage(e);
		}
		
		String returnString = resp.toJSONString();
		return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
	}
	
	
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/products/price/edit.html", method=RequestMethod.GET)
	public String editProductPrice(@RequestParam("id") long productPriceId, @RequestParam("productId") long productId,Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		Product product = productService.getById(productId);
		
		if(product==null) {
			return "redirect:/admin/products/products.html";
		}
		
		if(product.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
			return "redirect:/admin/products/products.html";
		}
		
		
		setMenu(model,request);
		return displayProductPrice(product, productPriceId, model, request, response);
		
	}
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/products/price/create.html", method=RequestMethod.GET)
	public String displayCreateProductPrice(@RequestParam("productId") long productId,@RequestParam("availabilityId") long avilabilityId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		Product product = productService.getById(productId);
		if(product==null) {
			return "redirect:/admin/products/products.html";
		}
		
		if(product.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
			return "redirect:/admin/products/products.html";
		}
		
		setMenu(model,request);
		return displayProductPrice(product, null, model, request, response);


		
	}
	
	private String displayProductPrice(Product product, Long productPriceId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);

		me.alanx.ecomer.web.dto.admin.catalog.ProductPrice pprice = new me.alanx.ecomer.web.dto.admin.catalog.ProductPrice();
		
		ProductPrice productPrice = null;
		ProductAvailability productAvailability = null;
		
		if(productPriceId!=null) {
		
			Set<ProductAvailability> availabilities = product.getAvailabilities();
	
			//get default availability
			for(ProductAvailability availability : availabilities) {
				if(availability.getRegion().equals(me.alanx.ecomer.core.constants.Constants.ALL_REGIONS)) {//TODO to be updated when multiple regions is implemented
					productAvailability = availability;
					Set<ProductPrice> prices = availability.getPrices();
					for(ProductPrice price : prices) {
						if(price.getId().longValue()==productPriceId.longValue()) {
							productPrice = price;
							if(price.getProductPriceSpecialStartDate()!=null) {
								pprice.setProductPriceSpecialStartDate(DateUtil.formatDate(price.getProductPriceSpecialStartDate()));
							}
							if(price.getProductPriceSpecialEndDate()!=null) {
								pprice.setProductPriceSpecialEndDate(DateUtil.formatDate(price.getProductPriceSpecialEndDate()));
							}
							pprice.setPriceText(ProductPriceUtils.getAdminFormatedAmount(store, price.getProductPriceAmount()));
							if(price.getProductPriceSpecialAmount()!=null) {
								pprice.setSpecialPriceText(ProductPriceUtils.getAdminFormatedAmount(store, price.getProductPriceSpecialAmount()));
							}
							break;
						}
					}
				}
			}
		
		}	
		
		if(productPrice==null) {
			productPrice = new ProductPrice();
			productPrice.setProductPriceType(ProductPriceType.ONE_TIME);
		}
		
		//descriptions
		List<Language> languages = store.getLanguages();
		
		Set<ProductPriceDescription> productPriceDescriptions = productPrice.getDescriptions();
		List<ProductPriceDescription> descriptions = new ArrayList<ProductPriceDescription>();
		for(Language l : languages) {
			ProductPriceDescription productPriceDesc = null;
			for(ProductPriceDescription desc : productPriceDescriptions) {
				Language lang = desc.getLanguage();
				if(lang.getCode().equals(l.getCode())) {
					productPriceDesc = desc;
				}
			}
			
			if(productPriceDesc==null) {
				productPriceDesc = new ProductPriceDescription();
				productPriceDesc.setLanguage(l);
				productPriceDescriptions.add(productPriceDesc);
			}	
			descriptions.add(productPriceDesc);
		}
		
		
		if(productAvailability==null) {
			Set<ProductAvailability> availabilities = product.getAvailabilities();
			for(ProductAvailability availability : availabilities) {
				if(availability.getRegion().equals(me.alanx.ecomer.core.constants.Constants.ALL_REGIONS)) {//TODO to be updated when multiple regions is implemented
					productAvailability = availability;
					break;
				}
			}
		}
		
		pprice.setDescriptions(descriptions);
		pprice.setProductAvailability(productAvailability);
		pprice.setPrice(productPrice);
		pprice.setProduct(product);
		

		model.addAttribute("product",product);
		//model.addAttribute("descriptions",descriptions);
		model.addAttribute("price",pprice);
		//model.addAttribute("availability",productAvailability);
		
		return ControllerConstants.Tiles.Product.productPrice;
	}
	
	
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/products/price/save.html", method=RequestMethod.POST)
	public String saveProductPrice(@Valid @ModelAttribute("price") me.alanx.ecomer.web.dto.admin.catalog.ProductPrice price, BindingResult result, Model model, HttpServletRequest request, Locale locale) throws Exception {
		
		//dates after save
		
		setMenu(model,request);
		
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		Product product = price.getProduct();
		Product dbProduct = productService.getById(product.getId());
		if(store.getId().intValue()!=dbProduct.getMerchantStore().getId().intValue()) {
			return "redirect:/admin/products/products.html";
		}
		
		model.addAttribute("product",dbProduct);
		
		//validate price
		BigDecimal submitedPrice = null;
		try {
			submitedPrice = ProductPriceUtils.getAmount(price.getPriceText());
		} catch (Exception e) {
			ObjectError error = new ObjectError("productPrice",messages.getMessage("NotEmpty.product.productPrice", locale));
			result.addError(error);
		}
		
		//validate discount price
		BigDecimal submitedDiscountPrice = null;
		
		if(!StringUtils.isBlank(price.getSpecialPriceText())) {
			try {
				submitedDiscountPrice = ProductPriceUtils.getAmount(price.getSpecialPriceText());
			} catch (Exception e) {
				ObjectError error = new ObjectError("productSpecialPrice",messages.getMessage("NotEmpty.product.productPrice", locale));
				result.addError(error);
			}
		}
		
		//validate start date
		if(!StringUtils.isBlank(price.getProductPriceSpecialStartDate())) {
			try {
				Date startDate = DateUtil.getDate(price.getProductPriceSpecialStartDate());
				price.getPrice().setProductPriceSpecialStartDate(startDate);
			} catch (Exception e) {
				ObjectError error = new ObjectError("productPriceSpecialStartDate",messages.getMessage("message.invalid.date", locale));
				result.addError(error);
			}
		}
		
		if(!StringUtils.isBlank(price.getProductPriceSpecialEndDate())) {
			try {
				Date endDate = DateUtil.getDate(price.getProductPriceSpecialEndDate());
				price.getPrice().setProductPriceSpecialEndDate(endDate);
			} catch (Exception e) {
				ObjectError error = new ObjectError("productPriceSpecialEndDate",messages.getMessage("message.invalid.date", locale));
				result.addError(error);
			}
		}
		
		
		if (result.hasErrors()) {
			return ControllerConstants.Tiles.Product.productPrice;
		}
		

		price.getPrice().setProductPriceAmount(submitedPrice);
		if(!StringUtils.isBlank(price.getSpecialPriceText())) {
			price.getPrice().setProductPriceSpecialAmount(submitedDiscountPrice);
		}
		
		ProductAvailability productAvailability = null;
		
		Set<ProductAvailability> availabilities = dbProduct.getAvailabilities();
		for(ProductAvailability availability : availabilities) {
			
			if(availability.getId().longValue()==price.getProductAvailability().getId().longValue()) {
				productAvailability = availability;
				break;
			}
			
			
		}
		
		
		
		
		Set<ProductPriceDescription> descriptions = new HashSet<ProductPriceDescription>();
		if(price.getDescriptions()!=null && price.getDescriptions().size()>0) {
			
			for(ProductPriceDescription description : price.getDescriptions()) {
				description.setProductPrice(price.getPrice());
				descriptions.add(description);
				description.setProductPrice(price.getPrice());
			}
		}
		
		price.getPrice().setDescriptions(descriptions);
		price.getPrice().setProductAvailability(productAvailability);
		
		productPriceService.saveOrUpdate(price.getPrice());
		model.addAttribute("success","success");
		
		return ControllerConstants.Tiles.Product.productPrice;
		
	}
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/products/price/remove.html", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> deleteProductPrice(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		String sPriceid = request.getParameter("priceId");

		
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		AjaxResponse resp = new AjaxResponse();
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

		
		try {
			
			Long priceId = Long.parseLong(sPriceid);
			ProductPrice price = productPriceService.getById(priceId);
			

			if(price==null || price.getProductAvailability().getProduct().getMerchantStore().getId().intValue()!=store.getId()) {

				resp.setStatusMessage(messages.getMessage("message.unauthorized", locale));
				resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);			
				String returnString = resp.toJSONString();
				return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
			} 
			
			productPriceService.delete(price);
			
			
			resp.setStatus(AjaxResponse.RESPONSE_OPERATION_COMPLETED);

		
		
		} catch (Exception e) {
			LOGGER.error("Error while deleting product price", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
			resp.setErrorMessage(e);
		}
		
		String returnString = resp.toJSONString();
		return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
	}
		
	
	private void setMenu(Model model, HttpServletRequest request) throws Exception {
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("catalogue", "catalogue");
		activeMenus.put("catalogue-products", "catalogue-products");
		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu)menus.get("catalogue");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
		//
		
	}

}