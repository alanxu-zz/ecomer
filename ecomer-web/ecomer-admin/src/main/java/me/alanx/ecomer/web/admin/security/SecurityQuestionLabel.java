package me.alanx.ecomer.web.admin.security;

import java.io.Serializable;

import me.alanx.ecomer.core.model.auth.SecurityQuestion;

public class SecurityQuestionLabel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final SecurityQuestion question;
	private final String label;

	
	
	public SecurityQuestionLabel(SecurityQuestion question, String label) {
		super();
		this.question = question;
		this.label = label;
	}


	public String getLabel() {
		return label;
	}
	/**
	 * @return the question
	 */
	public SecurityQuestion getQuestion() {
		return question;
	}

	

}
