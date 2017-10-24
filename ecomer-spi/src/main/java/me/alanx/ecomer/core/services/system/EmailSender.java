package me.alanx.ecomer.core.services.system;

import me.alanx.ecomer.core.model.email.Email;
import me.alanx.ecomer.core.model.email.EmailConfig;

public interface EmailSender {
	
	public void send(final Email email) throws Exception;

	public void setEmailConfig(EmailConfig emailConfig);

}
