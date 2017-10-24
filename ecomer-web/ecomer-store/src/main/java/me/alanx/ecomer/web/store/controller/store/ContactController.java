package me.alanx.ecomer.web.store.controller.store;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import me.alanx.ecomer.core.model.content.Content;
import me.alanx.ecomer.core.model.content.ContentDescription;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.content.ContentService;
import me.alanx.ecomer.web.ajax.AjaxResponse;
import me.alanx.ecomer.web.constants.ApplicationConstants;
import me.alanx.ecomer.web.constants.Constants;
import me.alanx.ecomer.web.dto.shop.ContactForm;
import me.alanx.ecomer.web.dto.shop.PageInformation;
import me.alanx.ecomer.web.store.controller.AbstractController;
import me.alanx.ecomer.web.store.controller.ControllerConstants;
import me.alanx.ecomer.web.utils.CaptchaRequestUtils;
import me.alanx.ecomer.web.utils.EmailTemplatesUtils;
import me.alanx.ecomer.web.utils.LabelUtils;
import me.alanx.ecomer.web.utils.LocaleUtils;

@Controller
public class ContactController extends AbstractController {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ContactController.class);
	
	@Inject
	private ContentService contentService;
	
	@Autowired
	private Environment coreConfiguration;
	
	@Inject
	private LabelUtils messages;
	
	@Inject
	private EmailTemplatesUtils emailTemplatesUtils;
	
	@Inject
	private CaptchaRequestUtils captchaRequestUtils;
	
	private final static String CONTACT_LINK = "CONTACT";
	
	
	@RequestMapping("/shop/store/contactus.html")
	public String displayContact(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.MERCHANT_STORE);
		
		request.setAttribute(Constants.LINK_CODE, CONTACT_LINK);

		Language language = (Language)request.getAttribute("LANGUAGE");
		
		ContactForm contact = new ContactForm();
		model.addAttribute("contact", contact);
		
		model.addAttribute( "recapatcha_public_key", coreConfiguration.getProperty( ApplicationConstants.RECAPTCHA_PUBLIC_KEY ) );
		
		Content content = contentService.getByCode(Constants.CONTENT_CONTACT_US, store, language);
		ContentDescription contentDescription = null;
		if(content!=null && content.isVisible()) {
			contentDescription = content.getDescription();
		}
		
		if(contentDescription!=null) {

			//meta information
			PageInformation pageInformation = new PageInformation();
			pageInformation.setPageDescription(contentDescription.getMetatagDescription());
			pageInformation.setPageKeywords(contentDescription.getMetatagKeywords());
			pageInformation.setPageTitle(contentDescription.getTitle());
			pageInformation.setPageUrl(contentDescription.getName());
			
			request.setAttribute(Constants.REQUEST_PAGE_INFORMATION, pageInformation);
			
			model.addAttribute("content",contentDescription);

		} 

		
		
		/** template **/
		StringBuilder template = new StringBuilder().append(ControllerConstants.Tiles.Content.contactus).append(".").append(store.getStoreTemplate());
		return template.toString();
		
		
	}

	
	@RequestMapping(value={"/shop/store/{storeCode}/contact"}, method=RequestMethod.POST)
	public @ResponseBody String sendEmail(@ModelAttribute(value="contact") ContactForm contact, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		AjaxResponse ajaxResponse = new AjaxResponse();
		
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.MERCHANT_STORE);

		try {
			
			//if ( StringUtils.isBlank( contact.getCaptchaResponseField() )) {
    		//	FieldError error = new FieldError("captchaResponseField","captchaResponseField",messages.getMessage("NotEmpty.contact.captchaResponseField", locale));
    		//	bindingResult.addError(error);
	        //    ajaxResponse.setErrorString(bindingResult.getAllErrors().get(0).getDefaultMessage());
	        //    ajaxResponse.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
	        //    return ajaxResponse.toJSONString();
			//}

	        //ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
	        //reCaptcha.setPublicKey( coreConfiguration.getProperty( ApplicationConstants.RECAPTCHA_PUBLIC_KEY));
	        //reCaptcha.setPrivateKey( coreConfiguration.getProperty( ApplicationConstants.RECAPTCHA_PRIVATE_KEY ) );
	        
/*	        if ( StringUtils.isNotBlank( contact.getCaptchaChallengeField() )
	                && StringUtils.isNotBlank( contact.getCaptchaResponseField() ) )
	            {
	                ReCaptchaResponse reCaptchaResponse =
	                    reCaptcha.checkAnswer( request.getRemoteAddr(), contact.getCaptchaChallengeField(),
	                                           contact.getCaptchaResponseField() );
	                if ( !reCaptchaResponse.isValid() )
	                {
	                    LOGGER.debug( "Captcha response does not matched" );
	        			FieldError error = new FieldError("captchaChallengeField","captchaChallengeField",messages.getMessage("validaion.recaptcha.not.matched", locale));
	        			bindingResult.addError(error);
	                }

	        }*/
	        
	        if(!StringUtils.isBlank(request.getParameter("g-recaptcha-response"))) {
	        	boolean validateCaptcha = captchaRequestUtils.checkCaptcha(request.getParameter("g-recaptcha-response"));
	        	
                if ( !validateCaptcha )
                {
                    LOGGER.debug( "Captcha response does not matched" );
        			FieldError error = new FieldError("captchaChallengeField","captchaChallengeField",messages.getMessage("validaion.recaptcha.not.matched", locale));
        			bindingResult.addError(error);
                }
	        }
	        
	        
	        
	        if ( bindingResult.hasErrors() )
	        {
	            LOGGER.debug( "found {} validation error while validating in customer registration ",
	                         bindingResult.getErrorCount() );
	            ajaxResponse.setErrorString(bindingResult.getAllErrors().get(0).getDefaultMessage());
	            ajaxResponse.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
	            return ajaxResponse.toJSONString();

	        }
	        
	        emailTemplatesUtils.sendContactEmail(contact, store, LocaleUtils.getLocale(store.getDefaultLanguage()), request.getContextPath());
			
			ajaxResponse.setStatus(AjaxResponse.RESPONSE_STATUS_SUCCESS);
		} catch(Exception e) {
			LOGGER.error("An error occured while trying to send an email",e);
			ajaxResponse.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
		}
		
		return ajaxResponse.toJSONString();
		
		
	}
	
	
}
