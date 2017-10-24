package me.alanx.ecomer.web.admin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.alanx.ecomer.core.model.auth.User;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.auth.UserService;
import me.alanx.ecomer.core.services.merchant.MerchantStoreService;
import me.alanx.ecomer.core.services.reference.language.LanguageService;
import me.alanx.ecomer.web.constants.Constants;
import me.alanx.ecomer.web.dto.admin.web.Menu;
import me.alanx.ecomer.web.utils.RequestUtils;

@Component
public class EComerAdminInterceptor extends HandlerInterceptorAdapter {
	

	private static final Logger log = LoggerFactory.getLogger(EComerAdminInterceptor.class);
	
	@Inject
	private MerchantStoreService merchantService;
	
	@Inject
	private UserService userService;
	
	@Inject
	private LanguageService languageService;
	
	@Inject
	private RequestUtils languageUtils;
	
	public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		@SuppressWarnings("unchecked")
		Map<String,Menu> menus = new HashMap<String,Menu>();
		
		User user = (User)request.getSession().getAttribute(Constants.USER);
		

		String storeCode = MerchantStore.DEFAULT_STORE;
		MerchantStore store = (MerchantStore)request.getSession().getAttribute(Constants.ADMIN_STORE);
		
		
		String userName = request.getRemoteUser();
		
		if(userName==null) {//** IMPORTANT FOR SPRING SECURITY **//
			//response.sendRedirect(new StringBuilder().append(request.getContextPath()).append("/").append("/admin").toString());
		} else {
		
			if(user==null) {
				user = userService.getByUserName(userName);
				request.getSession().setAttribute(Constants.USER, user);
				if(user!=null) {
					storeCode = user.getMerchantStore().getCode();
				} else {
					log.warn("User name not found " + userName);
				}
				store=null;
			}
			
			if(user==null) {
				response.sendRedirect(request.getContextPath() + "/admin/unauthorized.html");
				return true;
			}
			
			if(!user.getUsername().equals(userName)) {
				user = userService.getByUserName(userName);
				if(user!=null) {
					storeCode = user.getMerchantStore().getCode();
				} else {
					log.warn("User name not found " + userName);
				}
				store=null;
			}
		
		}
		
		if(store==null) {
				store = merchantService.getByCode(storeCode);
				request.getSession().setAttribute(Constants.ADMIN_STORE, store);
		}
		request.setAttribute(Constants.ADMIN_STORE, store);
		
		
		Language language = languageUtils.getLanguage(request, response);
		
		//Language language = (Language) request.getSession().getAttribute("LANGUAGE");

		if(language==null) {
			
			//TODO get the Locale from Spring API, is it simply request.getLocale() ???
			//if so then based on the Locale language locale.getLanguage() get the appropriate Language
			//object as represented below
			if(user!=null) {
				language = user.getDefaultLanguage();
				if(language==null) {
					language = store.getDefaultLanguage();
				}
			} else {
				language = store.getDefaultLanguage();
			}

			request.getSession().setAttribute("LANGUAGE", language);

		}
		

		request.setAttribute(Constants.LANGUAGE, language);
		

			InputStream in = null;
			ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
			try {
				in =
					(InputStream) this.getClass().getClassLoader().getResourceAsStream("config/menu.json");

				Map<String,Object> data = mapper.readValue(in, Map.class);

				Menu currentMenu = null;
				
				menus = new LinkedHashMap<String,Menu>();
				List objects = (List)data.get("menus");
				for(Object object : objects) {
					Menu m = getMenu(object);
					menus.put(m.getCode(),m);
				}

				//cache.putInCache(menus,"MENUMAP");

			} catch (JsonParseException e) {
				log.error("Error while creating menu", e);
			} catch (JsonMappingException e) {
				log.error("Error while creating menu", e);
			} catch (IOException e) {
				log.error("Error while creating menu", e);
			} finally {
				if(in !=null) {
					try {
						in.close();
					} catch (Exception ignore) {
						// TODO: handle exception
					}
				}
			}
		
		
		
		List<Menu> list = new ArrayList<Menu>(menus.values());

		request.setAttribute("MENULIST", list);
		request.setAttribute("MENUMAP", menus);
		response.setCharacterEncoding("UTF-8");
		
		return true;
	}
	
	
	private Menu getMenu(Object object) {
		
		Map o = (Map)object;
		Map menu = (Map)o.get("menu");
		
		Menu m = new Menu();
		m.setCode((String)menu.get("code"));
		
		
		m.setUrl((String)menu.get("url"));
		m.setIcon((String)menu.get("icon"));
		m.setRole((String)menu.get("role"));
		
		List menus = (List)menu.get("menus");
		if(menus!=null) {
			for(Object oo : menus) {
				
				Menu mm = getMenu(oo);
				m.getMenus().add(mm);
			}
			
		}
		
		return m;
		
	}

}
