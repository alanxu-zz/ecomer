package me.alanx.ecomer.web.admin.controller.orders;


import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.email.Email;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.Order;
import me.alanx.ecomer.core.model.order.OrderTotal;
import me.alanx.ecomer.core.model.order.product.OrderProduct;
import me.alanx.ecomer.core.model.order.product.OrderProductDownload;
import me.alanx.ecomer.core.model.order.status.OrderStatusHistory;
import me.alanx.ecomer.core.model.payments.PaymentType;
import me.alanx.ecomer.core.model.payments.Transaction;
import me.alanx.ecomer.core.model.reference.Country;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.reference.Zone;
import me.alanx.ecomer.core.services.catalog.product.PricingService;
import me.alanx.ecomer.core.services.customer.CustomerService;
import me.alanx.ecomer.core.services.order.OrderService;
import me.alanx.ecomer.core.services.order.orderproduct.OrderProductDownloadService;
import me.alanx.ecomer.core.services.payments.PaymentService;
import me.alanx.ecomer.core.services.payments.PaymentSupportService;
import me.alanx.ecomer.core.services.payments.TransactionService;
import me.alanx.ecomer.core.services.reference.country.CountryService;
import me.alanx.ecomer.core.services.reference.zone.ZoneService;
import me.alanx.ecomer.core.services.system.EmailService;
import me.alanx.ecomer.web.admin.controller.ControllerConstants;
import me.alanx.ecomer.web.constants.Constants;
import me.alanx.ecomer.web.constants.EmailConstants;
import me.alanx.ecomer.web.dto.admin.web.Menu;
import me.alanx.ecomer.web.utils.DateUtil;
import me.alanx.ecomer.web.utils.EmailUtils;
import me.alanx.ecomer.web.utils.LabelUtils;
import me.alanx.ecomer.web.utils.LocaleUtils;

/**
 * Manage order details
 * @author Carl Samson
 *
 */
@Controller
public class OrderControler {
	
private static final Logger LOGGER = LoggerFactory.getLogger(OrderControler.class);
	
	@Inject
	private LabelUtils messages;
	
	@Inject
	private OrderService orderService;
	
	@Inject
	CountryService countryService;
	
	@Inject
	ZoneService zoneService;
	
	@Inject
	PaymentSupportService paymentService;
	
	@Inject
	CustomerService customerService;
	
	@Inject
	PricingService pricingService;
	
	@Inject
	TransactionService transactionService;
	
	@Inject
	EmailService emailService;
	
	@Inject
	private EmailUtils emailUtils;
	
	@Inject
	OrderProductDownloadService orderProdctDownloadService;
	
	private final static String ORDER_STATUS_TMPL = "email_template_order_status.ftl";
	

	@PreAuthorize("hasRole('ORDER')")
	@RequestMapping(value="/admin/orders/editOrder.html", method=RequestMethod.GET)
	public String displayOrderEdit(@RequestParam("id") long orderId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		return displayOrder(orderId,model,request,response);

	}

	@PreAuthorize("hasRole('ORDER')")
	private String displayOrder(Long orderId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//display menu
		setMenu(model,request);
		   
		me.alanx.ecomer.web.dto.admin.orders.Order order = new me.alanx.ecomer.web.dto.admin.orders.Order();
		Language language = (Language)request.getAttribute("LANGUAGE");
		List<Country> countries = countryService.getCountries(language);
		if(orderId!=null && orderId!=0) {		//edit mode		
			
			
			MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
			
			
			
			Set<OrderProduct> orderProducts = null;
			Set<OrderTotal> orderTotal = null;
			Set<OrderStatusHistory> orderHistory = null;
		
			Order dbOrder = orderService.getById(orderId);

			if(dbOrder==null) {
				return "redirect:/admin/orders/list.html";
			}
			
			
			if(dbOrder.getMerchant().getId().intValue()!=store.getId().intValue()) {
				return "redirect:/admin/orders/list.html";
			}
			
			
			order.setId( orderId );
		
			if( dbOrder.getDatePurchased() !=null ){
				order.setDatePurchased(DateUtil.formatDate(dbOrder.getDatePurchased()));
			}
			
			Long customerId = dbOrder.getCustomerId();
			
			if(customerId!=null && customerId>0) {
			
				try {
					
					Customer customer = customerService.getById(customerId);
					if(customer!=null) {
						model.addAttribute("customer",customer);
					}
					
					
				} catch(Exception e) {
					LOGGER.error("Error while getting customer for customerId " + customerId, e);
				}
			
			}
			
			order.setOrder( dbOrder );
			order.setBilling( dbOrder.getBilling() );
			order.setDelivery(dbOrder.getDelivery() );
			

			orderProducts = dbOrder.getOrderProducts();
			orderTotal = dbOrder.getOrderTotal();
			orderHistory = dbOrder.getOrderHistory();
			
			//get capturable
			if(dbOrder.getPaymentType().name() != PaymentType.MONEYORDER.name()) {
				Transaction capturableTransaction = transactionService.getCapturableTransaction(dbOrder);
				if(capturableTransaction!=null) {
					model.addAttribute("capturableTransaction",capturableTransaction);
				}
			}
			
			
			//get refundable
			if(dbOrder.getPaymentType().name() != PaymentType.MONEYORDER.name()) {
				Transaction refundableTransaction = transactionService.getRefundableTransaction(dbOrder);
				if(refundableTransaction!=null) {
						model.addAttribute("capturableTransaction",null);//remove capturable
						model.addAttribute("refundableTransaction",refundableTransaction);
				}
			}

			
			List<OrderProductDownload> orderProductDownloads = orderProdctDownloadService.getByOrderId(order.getId());
			if(CollectionUtils.isNotEmpty(orderProductDownloads)) {
				model.addAttribute("downloads",orderProductDownloads);
			}
			
		}	
		
		model.addAttribute("countries", countries);
		model.addAttribute("order",order);
		return  ControllerConstants.Tiles.Order.ordersEdit;
	}
	

	@PreAuthorize("hasRole('ORDER')")
	@RequestMapping(value="/admin/orders/save.html", method=RequestMethod.POST)
	public String saveOrder(@Valid @ModelAttribute("order") me.alanx.ecomer.web.dto.admin.orders.Order entityOrder, BindingResult result, Model model, HttpServletRequest request, Locale locale) throws Exception {
		
		String email_regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
		Pattern pattern = Pattern.compile(email_regEx);
		
		Language language = (Language)request.getAttribute("LANGUAGE");
		List<Country> countries = countryService.getCountries(language);
		model.addAttribute("countries", countries);
		
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		//set the id if fails
		entityOrder.setId(entityOrder.getOrder().getId());
		
		model.addAttribute("order", entityOrder);
		
		Set<OrderProduct> orderProducts = new HashSet<OrderProduct>();
		Set<OrderTotal> orderTotal = new HashSet<OrderTotal>();
		Set<OrderStatusHistory> orderHistory = new HashSet<OrderStatusHistory>();
		
		Date date = new Date();
		if(!StringUtils.isBlank(entityOrder.getDatePurchased() ) ){
			try {
				date = DateUtil.getDate(entityOrder.getDatePurchased());
			} catch (Exception e) {
				ObjectError error = new ObjectError("datePurchased",messages.getMessage("message.invalid.date", locale));
				result.addError(error);
			}
			
		} else{
			date = null;
		}
		 

		if(!StringUtils.isBlank(entityOrder.getOrder().getCustomerEmailAddress() ) ){
			 java.util.regex.Matcher matcher = pattern.matcher(entityOrder.getOrder().getCustomerEmailAddress());
			 
			 if(!matcher.find()) {
				ObjectError error = new ObjectError("customerEmailAddress",messages.getMessage("Email.order.customerEmailAddress", locale));
				result.addError(error);
			 }
		}else{
			ObjectError error = new ObjectError("customerEmailAddress",messages.getMessage("NotEmpty.order.customerEmailAddress", locale));
			result.addError(error);
		}

		 
		if( StringUtils.isBlank(entityOrder.getOrder().getBilling().getFirstName() ) ){
			 ObjectError error = new ObjectError("billingFirstName", messages.getMessage("NotEmpty.order.billingFirstName", locale));
			 result.addError(error);
		}
		
		if( StringUtils.isBlank(entityOrder.getOrder().getBilling().getFirstName() ) ){
			 ObjectError error = new ObjectError("billingLastName", messages.getMessage("NotEmpty.order.billingLastName", locale));
			 result.addError(error);
		}
		 
		if( StringUtils.isBlank(entityOrder.getOrder().getBilling().getAddress() ) ){
			 ObjectError error = new ObjectError("billingAddress", messages.getMessage("NotEmpty.order.billingStreetAddress", locale));
			 result.addError(error);
		}
		 
		if( StringUtils.isBlank(entityOrder.getOrder().getBilling().getCity() ) ){
			 ObjectError error = new ObjectError("billingCity",messages.getMessage("NotEmpty.order.billingCity", locale));
			 result.addError(error);
		}
		 
		if( entityOrder.getOrder().getBilling().getZone()==null){
			if( StringUtils.isBlank(entityOrder.getOrder().getBilling().getState())){
				 ObjectError error = new ObjectError("billingState",messages.getMessage("NotEmpty.order.billingState", locale));
				 result.addError(error);
			}
		}
		 
		if( StringUtils.isBlank(entityOrder.getOrder().getBilling().getPostalCode() ) ){
			 ObjectError error = new ObjectError("billingPostalCode", messages.getMessage("NotEmpty.order.billingPostCode", locale));
			 result.addError(error);
		}
		
		Order newOrder = orderService.getById(entityOrder.getOrder().getId() );
		
		
		//get capturable
		if(newOrder.getPaymentType().name() != PaymentType.MONEYORDER.name()) {
			Transaction capturableTransaction = transactionService.getCapturableTransaction(newOrder);
			if(capturableTransaction!=null) {
				model.addAttribute("capturableTransaction",capturableTransaction);
			}
		}
		
		
		//get refundable
		if(newOrder.getPaymentType().name() != PaymentType.MONEYORDER.name()) {
			Transaction refundableTransaction = transactionService.getRefundableTransaction(newOrder);
			if(refundableTransaction!=null) {
					model.addAttribute("capturableTransaction",null);//remove capturable
					model.addAttribute("refundableTransaction",refundableTransaction);
			}
		}
	
	
		if (result.hasErrors()) {
			//  somehow we lose data, so reset Order detail info.
			entityOrder.getOrder().setOrderProducts( orderProducts);
			entityOrder.getOrder().setOrderTotal(orderTotal);
			entityOrder.getOrder().setOrderHistory(orderHistory);
			
			return ControllerConstants.Tiles.Order.ordersEdit;
		/*	"admin-orders-edit";  */
		}
		
		OrderStatusHistory orderStatusHistory = new OrderStatusHistory();		



		
		Country deliveryCountry = countryService.getByCode( entityOrder.getOrder().getDelivery().getCountry().getIsoCode()); 
		Country billingCountry  = countryService.getByCode( entityOrder.getOrder().getBilling().getCountry().getIsoCode()) ;
		Zone billingZone = null;
		Zone deliveryZone = null;
		if(entityOrder.getOrder().getBilling().getZone()!=null) {
			billingZone = zoneService.getByCode(entityOrder.getOrder().getBilling().getZone().getCode());
		}
		
		if(entityOrder.getOrder().getDelivery().getZone()!=null) {
			deliveryZone = zoneService.getByCode(entityOrder.getOrder().getDelivery().getZone().getCode());
		}

		newOrder.setCustomerEmailAddress(entityOrder.getOrder().getCustomerEmailAddress() );
		newOrder.setStatus(entityOrder.getOrder().getStatus() );		
		
		newOrder.setDatePurchased(date);
		newOrder.setLastModified( new Date() );
		
		if(!StringUtils.isBlank(entityOrder.getOrderHistoryComment() ) ) {
			orderStatusHistory.setComments( entityOrder.getOrderHistoryComment() );
			orderStatusHistory.setCustomerNotified(1);
			orderStatusHistory.setStatus(entityOrder.getOrder().getStatus());
			orderStatusHistory.setDateAdded(new Date() );
			orderStatusHistory.setOrder(newOrder);
			newOrder.getOrderHistory().add( orderStatusHistory );
			entityOrder.setOrderHistoryComment( "" );
		}		
		
		newOrder.setDelivery( entityOrder.getOrder().getDelivery() );
		newOrder.setBilling( entityOrder.getOrder().getBilling() );
		newOrder.setCustomerAgreement(entityOrder.getOrder().getCustomerAgreement());
		
		newOrder.getDelivery().setCountry(deliveryCountry );
		newOrder.getBilling().setCountry(billingCountry );	
		
		if(billingZone!=null) {
			newOrder.getBilling().setZone(billingZone);
		}
		
		if(deliveryZone!=null) {
			newOrder.getDelivery().setZone(deliveryZone);
		}
		
		orderService.saveOrUpdate(newOrder);
		entityOrder.setOrder(newOrder);
		entityOrder.setBilling(newOrder.getBilling());
		entityOrder.setDelivery(newOrder.getDelivery());
		model.addAttribute("order", entityOrder);
		
		Long customerId = newOrder.getCustomerId();
		
		if(customerId!=null && customerId>0) {
		
			try {
				
				Customer customer = customerService.getById(customerId);
				if(customer!=null) {
					model.addAttribute("customer",customer);
				}
				
				
			} catch(Exception e) {
				LOGGER.error("Error while getting customer for customerId " + customerId, e);
			}
		
		}

		List<OrderProductDownload> orderProductDownloads = orderProdctDownloadService.getByOrderId(newOrder.getId());
		if(CollectionUtils.isNotEmpty(orderProductDownloads)) {
			model.addAttribute("downloads",orderProductDownloads);
		}
		
		
		/** 
		 * send email if admin posted orderHistoryComment
		 * 
		 * **/
		
		if(StringUtils.isBlank(entityOrder.getOrderHistoryComment())) {
		
			try {
				
				Customer customer = customerService.getById(newOrder.getCustomerId());
				Language lang = store.getDefaultLanguage();
				if(customer!=null) {
					lang = customer.getDefaultLanguage();
				}
				
				Locale customerLocale = LocaleUtils.getLocale(lang);

				StringBuilder customerName = new StringBuilder();
				customerName.append(newOrder.getBilling().getFirstName()).append(" ").append(newOrder.getBilling().getLastName());
				
				
				Map<String, String> templateTokens = emailUtils.createEmailObjectsMap(request.getContextPath(), store, messages, customerLocale);
				templateTokens.put(EmailConstants.EMAIL_CUSTOMER_NAME, customerName.toString());
				templateTokens.put(EmailConstants.EMAIL_TEXT_ORDER_NUMBER, messages.getMessage("email.order.confirmation", new String[]{String.valueOf(newOrder.getId())}, customerLocale));
				templateTokens.put(EmailConstants.EMAIL_TEXT_DATE_ORDERED, messages.getMessage("email.order.ordered", new String[]{entityOrder.getDatePurchased()}, customerLocale));
				templateTokens.put(EmailConstants.EMAIL_TEXT_STATUS_COMMENTS, messages.getMessage("email.order.comments", new String[]{entityOrder.getOrderHistoryComment()}, customerLocale));
				templateTokens.put(EmailConstants.EMAIL_TEXT_DATE_UPDATED, messages.getMessage("email.order.updated", new String[]{DateUtil.formatDate(new Date())}, customerLocale));

				
				Email email = new Email();
				email.setFrom(store.getStorename());
				email.setFromEmail(store.getStoreEmailAddress());
				email.setSubject(messages.getMessage("email.order.status.title",new String[]{String.valueOf(newOrder.getId())},customerLocale));
				email.setTo(entityOrder.getOrder().getCustomerEmailAddress());
				email.setTemplateName(ORDER_STATUS_TMPL);
				email.setTemplateTokens(templateTokens);
	
	
				
				emailService.sendEmail(store, email);
			
			} catch (Exception e) {
				LOGGER.error("Cannot send email to customer",e);
			}
			
		}
		
		model.addAttribute("success","success");

		
		return  ControllerConstants.Tiles.Order.ordersEdit;
	    /*	"admin-orders-edit";  */
	}

	private void setMenu(Model model, HttpServletRequest request) throws Exception {
	
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("order", "order");
		activeMenus.put("order-list", "order-list");
		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");

		model.addAttribute("activeMenus",activeMenus);
		
		Menu currentMenu = (Menu)menus.get("order");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
		//
		
	}

}
