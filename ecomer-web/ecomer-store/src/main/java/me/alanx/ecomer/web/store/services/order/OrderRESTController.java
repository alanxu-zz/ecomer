package me.alanx.ecomer.web.store.services.order;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.Order;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.catalog.product.ProductService;
import me.alanx.ecomer.core.services.catalog.product.attribute.ProductOptionPriceService;
import me.alanx.ecomer.core.services.catalog.product.file.DigitalProductService;
import me.alanx.ecomer.core.services.customer.CustomerService;
import me.alanx.ecomer.core.services.merchant.MerchantStoreService;
import me.alanx.ecomer.core.services.order.OrderService;
import me.alanx.ecomer.core.services.reference.language.LanguageService;
import me.alanx.ecomer.web.constants.Constants;
import me.alanx.ecomer.web.dto.customer.PersistableCustomer;
import me.alanx.ecomer.web.dto.order.PersistableOrder;
import me.alanx.ecomer.web.dto.order.ReadableOrderList;
import me.alanx.ecomer.web.populator.customer.CustomerPopulator;
import me.alanx.ecomer.web.populator.order.PersistableOrderPopulator;
import me.alanx.ecomer.web.store.controller.order.facade.OrderFacade;

@Controller
@RequestMapping("/services/private")
public class OrderRESTController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderRESTController.class);
	
	
	@Inject
	private MerchantStoreService merchantStoreService;
	
	@Inject
	private ProductService productService;
	
	@Inject
	private ProductOptionPriceService productAttributeService;
	
	@Inject
	private DigitalProductService digitalProductService;
	
	@Inject
	private OrderFacade orderFacade;
	
	@Inject
	private OrderService orderService;
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	private LanguageService languageService;

	
	@RequestMapping( value="/{store}/orders", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public PersistableOrder createOrder(@PathVariable final String store, @Valid @RequestBody PersistableOrder order, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MerchantStore merchantStore = (MerchantStore)request.getAttribute(Constants.MERCHANT_STORE);
		if(merchantStore!=null) {
			if(!merchantStore.getCode().equals(store)) {
				merchantStore = null;
			}
		}
		
		if(merchantStore== null) {
			merchantStore = merchantStoreService.getByCode(store);
		}
		
		if(merchantStore==null) {
			LOGGER.error("Merchant store is null for code " + store);
			response.sendError(503, "Merchant store is null for code " + store);
			return null;
		}
		
		
		PersistableCustomer cust = order.getCustomer();
		if(cust!=null) {
			CustomerPopulator customerPopulator = new CustomerPopulator();
			Customer customer = new Customer();
			customerPopulator.populate(cust, customer, merchantStore, merchantStore.getDefaultLanguage());
			customerService.save(customer);
			cust.setId(customer.getId());
		}
		
		
		Order modelOrder = new Order();
		PersistableOrderPopulator populator = new PersistableOrderPopulator();
		populator.setDigitalProductService(digitalProductService);
		populator.setProductAttributeService(productAttributeService);
		populator.setProductService(productService);
		
		populator.populate(order, modelOrder, merchantStore, merchantStore.getDefaultLanguage());
		
	
		orderService.save(modelOrder);
		order.setId(modelOrder.getId());
		
		return order;
	}
	
	
	/**
	 * Get a list of orders
	 * accept request parameter 'lang' [en,fr...] otherwise store dafault language
	 * accept request parameter 'start' start index for count
	 * accept request parameter 'max' maximum number count, otherwise returns all
	 * @param store
	 * @param order
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( value="/{store}/orders/", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ResponseBody
	public ReadableOrderList listOrders(@PathVariable final String store, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MerchantStore merchantStore = (MerchantStore)request.getAttribute(Constants.MERCHANT_STORE);
		if(merchantStore!=null) {
			if(!merchantStore.getCode().equals(store)) {
				merchantStore = null;
			}
		}
		
		if(merchantStore== null) {
			merchantStore = merchantStoreService.getByCode(store);
		}
		
		if(merchantStore==null) {
			LOGGER.error("Merchant store is null for code " + store);
			response.sendError(503, "Merchant store is null for code " + store);
			return null;
		}
		
		//get additional request parameters for orders
		String lang = request.getParameter(Constants.LANG);		
		String start = request.getParameter(Constants.START);
		String max = request.getParameter(Constants.MAX);
		
		int startCount = 0;
		int maxCount = 0;
		
		if(StringUtils.isBlank(lang)) {
			lang = merchantStore.getDefaultLanguage().getCode();
		}
		
		
		Language language = languageService.getByCode(lang);
		
		if(language==null) {
			LOGGER.error("Language is null for code " + lang);
			response.sendError(503, "Language is null for code " + lang);
			return null;
		}
		
		try {
			startCount = Integer.parseInt(start);
		} catch (Exception e) {
			LOGGER.info("Invalid value for start " + start);
		}
		
		try {
			maxCount = Integer.parseInt(max);
		} catch (Exception e) {
			LOGGER.info("Invalid value for max " + max);
		}
		
		
		
		ReadableOrderList returnList = orderFacade.getReadableOrderList(merchantStore, startCount, maxCount, language);

		return returnList;
	}
	
	/**
	 * Get a list of orders for a given customer
	 * accept request parameter 'lang' [en,fr...] otherwise store dafault language
	 * accept request parameter 'start' start index for count
	 * accept request parameter 'max' maximum number count, otherwise returns all
	 * @param store
	 * @param order
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( value="/{store}/orders/customer/{id}", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ResponseBody
	public ReadableOrderList listOrders(@PathVariable final String store, @PathVariable final Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MerchantStore merchantStore = (MerchantStore)request.getAttribute(Constants.MERCHANT_STORE);
		if(merchantStore!=null) {
			if(!merchantStore.getCode().equals(store)) {
				merchantStore = null;
			}
		}
		
		if(merchantStore== null) {
			merchantStore = merchantStoreService.getByCode(store);
		}
		
		if(merchantStore==null) {
			LOGGER.error("Merchant store is null for code " + store);
			response.sendError(503, "Merchant store is null for code " + store);
			return null;
		}
		
		//get additional request parameters for orders
		String lang = request.getParameter(Constants.LANG);		
		String start = request.getParameter(Constants.START);
		String max = request.getParameter(Constants.MAX);
		
		int startCount = 0;
		int maxCount = 0;
		
		if(StringUtils.isBlank(lang)) {
			lang = merchantStore.getDefaultLanguage().getCode();
		}
		
		
		Language language = languageService.getByCode(lang);
		
		if(language==null) {
			LOGGER.error("Language is null for code " + lang);
			response.sendError(503, "Language is null for code " + lang);
			return null;
		}
		
		try {
			startCount = Integer.parseInt(start);
		} catch (Exception e) {
			LOGGER.info("Invalid value for start " + start);
		}
		
		try {
			maxCount = Integer.parseInt(max);
		} catch (Exception e) {
			LOGGER.info("Invalid value for max " + max);
		}
		
		Customer customer = customerService.getById(id);
		
		if(customer==null) {
			LOGGER.error("Customer is null for id " + id);
			response.sendError(503, "Customer is null for id " + id);
			return null;
		}
		
		if(customer.getMerchantStore().getId().intValue()!=merchantStore.getId().intValue()) {
			LOGGER.error("Customer is null for id " + id + " and store id " + store);
			response.sendError(503, "Customer is null for id " + id + " and store id " + store);
			return null;
		}
		
		ReadableOrderList returnList = orderFacade.getReadableOrderList(merchantStore, startCount, maxCount, language);

		return returnList;
	}

}
