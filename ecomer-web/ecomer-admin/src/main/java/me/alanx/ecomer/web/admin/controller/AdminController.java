package me.alanx.ecomer.web.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import me.alanx.ecomer.core.model.auth.User;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Country;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.auth.UserService;
import me.alanx.ecomer.core.services.reference.country.CountryService;
import me.alanx.ecomer.web.constants.Constants;



@Controller
public class AdminController {
	
	
	private static final Logger log = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	Environment env;
	
	@Inject
	CountryService countryService;
	
	@Inject
	UserService userService;
	
	
	@PreAuthorize("hasRole('AUTH')")
	@RequestMapping(value={"/admin/home.html","/admin/","/admin"}, method=RequestMethod.GET)
	public String displayDashboard(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		Language language = (Language)request.getAttribute("LANGUAGE");
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("home", "home");
		
		model.addAttribute("activeMenus",activeMenus);
		
		
		//get store information
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		Map<String,Country> countries = countryService.getCountriesMap(language);
		
		Country storeCountry = store.getCountry();
		Country country = countries.get(storeCountry.getIsoCode());
		
		String sCurrentUser = request.getRemoteUser();
		User currentUser = userService.getByUserName(sCurrentUser);
		model.addAttribute("store", store);
		model.addAttribute("country", country);
		model.addAttribute("user", currentUser);
		//get last 10 orders
		//OrderCriteria orderCriteria = new OrderCriteria();
		//orderCriteria.setMaxCount(10);
		//orderCriteria.setOrderBy(CriteriaOrderBy.DESC);
		
		return ControllerConstants.Tiles.adminDashboard;
	}

	@GetMapping("/")
	public String home() {
		log.info("========= HHHHHHHHHHHHHHHH ========");
		return "admin-dashboard";
	}
}
