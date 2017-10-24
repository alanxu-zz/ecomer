package me.alanx.ecomer.web.error;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class EComerErrorViewResolver implements ErrorViewResolver {

	
	private static final Logger log = LoggerFactory.getLogger(EComerErrorViewResolver.class);

	
	public static final String ATTRIBUTE_EXCPETION = "exception";
	public static final String ATTRIBUTE_ERROR_STACK = "stackError";
	public static final String ATTRIBUTE_STATUS= "status";
	
	@Override
	public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {

		ModelAndView mv = new ModelAndView("error/generic_error", model);
		
		Throwable ex = (Throwable)request.getAttribute("javax.servlet.error.exception");
		
		if (ex != null) {
			log.error("View error: {}", ex);
			
			mv.getModel().put(ATTRIBUTE_EXCPETION, ex);
			mv.getModel().put(ATTRIBUTE_ERROR_STACK, ExceptionUtils.getStackTrace(ex));
			mv.getModel().put(ATTRIBUTE_STATUS, status.value());
			
		}
		
		return mv;
	}

}
