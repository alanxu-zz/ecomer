package me.alanx.ecomer.web.admin.controller.orders;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import me.alanx.ecomer.core.model.common.CriteriaOrderBy;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.Order;
import me.alanx.ecomer.core.model.order.OrderCriteria;
import me.alanx.ecomer.core.model.order.OrderList;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.system.IntegrationModule;
import me.alanx.ecomer.core.services.order.OrderService;
import me.alanx.ecomer.core.services.system.ModuleConfigurationService;
import me.alanx.ecomer.core.utils.ProductPriceUtils;
import me.alanx.ecomer.web.admin.controller.ControllerConstants;
import me.alanx.ecomer.web.ajax.AjaxPageableResponse;
import me.alanx.ecomer.web.ajax.AjaxResponse;
import me.alanx.ecomer.web.constants.Constants;
import me.alanx.ecomer.web.dto.admin.web.Menu;
import me.alanx.ecomer.web.utils.DateUtil;
import me.alanx.ecomer.web.utils.LabelUtils;


/**
 * Manage order list
 * Manage search order
 * @author csamson 
 *
 */
@Controller
@JsonAutoDetect(getterVisibility=com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE)
public class OrdersController {
	
	@Inject
	OrderService orderService;
	
	@Inject
	LabelUtils messages;
	
	@Inject
	protected ModuleConfigurationService moduleConfigurationService;
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderControler.class);

	
	@PreAuthorize("hasRole('ORDER')")
	@RequestMapping(value="/admin/orders/list.html", method=RequestMethod.GET)
	public String displayOrders(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		setMenu(model,request);

		//the list of orders is from page method
		
		return ControllerConstants.Tiles.Order.orders;
		
		
	}


	@PreAuthorize("hasRole('ORDER')")
	@SuppressWarnings({ "unchecked", "unused"})
	@RequestMapping(value="/admin/orders/paging.html", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> pageOrders(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		

		AjaxPageableResponse resp = new AjaxPageableResponse();

		try {
			
			int startRow = Integer.parseInt(request.getParameter("_startRow"));
			int endRow = Integer.parseInt(request.getParameter("_endRow"));
			String	paymentModule = request.getParameter("paymentModule");
			String customerName = request.getParameter("customer");
			
			OrderCriteria criteria = new OrderCriteria();
			criteria.setOrderBy(CriteriaOrderBy.DESC);
			criteria.setStartIndex(startRow);
			criteria.setMaxCount(endRow);
			if(!StringUtils.isBlank(paymentModule)) {
				criteria.setPaymentMethod(paymentModule);
			}
			
			if(!StringUtils.isBlank(customerName)) {
				criteria.setCustomerName(customerName);
			}
			
			Language language = (Language)request.getAttribute("LANGUAGE");
			MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
			List<IntegrationModule> paymentModules = moduleConfigurationService.getIntegrationModules( "PAYMENT" );


			OrderList orderList = orderService.listByStore(store, criteria);
		
			if(orderList.getOrders()!=null) {	
			
				for(Order order : orderList.getOrders()) {
					
					@SuppressWarnings("rawtypes")
					Map entry = new HashMap();
					entry.put("orderId", order.getId());
					entry.put("customer", order.getBilling().getFirstName() + " " + order.getBilling().getLastName());
					entry.put("amount", ProductPriceUtils.getAdminFormatedAmountWithCurrency(store,order.getTotal()));//todo format total
					entry.put("date", DateUtil.formatDate(order.getDatePurchased()));
					entry.put("status", order.getStatus().name());
					
					
					if ( paymentModules!= null && paymentModules.size() > 0 ) 
					{	
						for ( int index = 0; index < paymentModules.size(); index++ )
						{
							if ( paymentModules.get(index).getCode().equalsIgnoreCase( order.getPaymentModuleCode() ) )
							{
								 paymentModule = paymentModules.get(index).getCode();
								 break;
							}
						}
	
					}
	
					entry.put("paymentModule", paymentModule );
					resp.addDataEntry(entry);				
					
				}
			}
			
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_SUCCESS);
			

		
		} catch (Exception e) {
			LOGGER.error("Error while paging orders", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
		}
		
		String returnString = resp.toJSONString();

		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
	}
	
	
	private void setMenu(Model model, HttpServletRequest request) throws Exception {
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("order", "order");
		activeMenus.put("order-list", "order-list");

		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu)menus.get("order");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
		//
		
	}

}
