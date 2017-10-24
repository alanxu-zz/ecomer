package me.alanx.ecomer.core.model.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import me.alanx.ecomer.core.model.generic.ApplicationEntity;

@Entity
@Table(name="SECURITY_QUESTIONS")
public class SecurityQuestion extends ApplicationEntity<Long, SecurityQuestion> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR")
	private Long id;
	
	@NotNull
	@Column(name = "SECURITY_QUESTION_KEY", nullable = false, unique = true, updatable = false)
	private String questionKey;
	
	public SecurityQuestion(String questionKey) {
		super();
		this.questionKey = questionKey;
	}
	
	public SecurityQuestion() {}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	} 

	/**
	 * @return the question
	 */
	public String getQuestionKey() {
		return questionKey;
	}

	@Override
	public void setId(Long id) {
		throw new UnsupportedOperationException("Not allowed.");
	}
	
	
	
}
