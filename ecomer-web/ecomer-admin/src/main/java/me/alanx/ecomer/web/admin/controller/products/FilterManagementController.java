package me.alanx.ecomer.web.admin.controller.products;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import me.alanx.ecomer.core.bind.StringCollectionFomatter;
import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.filter.FilterDefinition;
import me.alanx.ecomer.core.utils.FilterUtils;
import me.alanx.ecomer.json.JsonUtils;
import me.alanx.ecomer.web.dto.admin.web.Menu;

@Controller
public class FilterManagementController {

	
	private static final Logger log = LoggerFactory.getLogger(FilterManagementController.class);

	@Autowired
	private PropertyEditorRegistrar customPropertyEditorRegistrar;

	
	@GetMapping("/admin/filter_management/list.html")
	public String filterList(Model model, HttpServletRequest request) throws Exception {
		setMenu(model, request);
		return "admin-products-filters";
	}
	
	@RequestMapping(value = "/admin/filter_management/new.html", method = {RequestMethod.GET})
	public String newFilter(@RequestParam(value = "prototype", required = false) String prototype, 
				@RequestParam(value = "categoryId", required = false) Long categoryId, Model model, HttpServletRequest request) throws Exception {
		setMenu(model, request);
		
		FilterDefinition fd = new FilterDefinition();
		
		fd.setOptionLabels(Arrays.asList("a", "b", "c"));
		model.addAttribute("filterDefinition", fd);
		
		return "admin-products-create";
	}
	
	@PostMapping(value = "/admin/filter_management/save.html")
	public String saveFilter(@ModelAttribute("filterDefinition") @Validated FilterDefinition fd, 
			BindingResult bindingResult, Model model, HttpServletRequest request) throws Exception {
		
		setMenu(model, request);
		
		if(bindingResult.hasErrors()) {
			return "admin-products-create";
		}
		
		JsonUtils.printJson(fd);
		
		return "admin-products-filters";
	}
	
	
	@GetMapping("/admin/api/filter-candidates/{entity}")
	@ResponseBody
	public Set<FilterDefinition> filterCandidates(@PathVariable String entity) {
		Class<?> clazz = null;
		if ("product".equalsIgnoreCase(entity)) {
			clazz = Product.class;
		}
		return FilterUtils.readFilterCandidates(clazz, new String[]{"me.alanx.ecomer.core.model"});
	}
	
	private void setMenu(Model model, HttpServletRequest request) throws Exception {
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("catalogue", "catalogue");
		activeMenus.put("filter-list", "filter-list");
		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu)menus.get("catalogue");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
		//
		
	}
	
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
		
		if (binder.getTarget() == null) return;
		
		if (binder.getTarget() instanceof FilterDefinition) {
			//binder.addValidators(new FilterDefinitionValidator());
			customPropertyEditorRegistrar.registerCustomEditors(binder);
			binder.addCustomFormatter(new StringCollectionFomatter(), "optionLabels", "optionValues");
		}
		
    }
	
	@ExceptionHandler(BindException.class)
	public String handleException(BindException ex, Model m) {
		
		log.error("Validation error",ex);

		
		return "admin-products-create"; 
	}
	
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("password"));
	}
}
