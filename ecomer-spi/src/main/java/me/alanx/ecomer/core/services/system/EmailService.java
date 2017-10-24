package me.alanx.ecomer.core.services.system;



import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.email.Email;
import me.alanx.ecomer.core.model.email.EmailConfig;
import me.alanx.ecomer.core.model.merchant.MerchantStore;



public interface EmailService {

	public void sendEmail(MerchantStore store, Email email) throws ServiceException, Exception;
	
	public EmailConfig getEmailConfiguration(MerchantStore store) throws ServiceException;
	
	public void setEmailConfiguration(EmailConfig emailConfig, MerchantStore store) throws ServiceException;
	
}
