package me.alanx.ecomer.core.model.generic;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import me.alanx.ecomer.core.model.common.audit.AuditSection;
import me.alanx.ecomer.core.model.common.audit.Auditable;

@MappedSuperclass
@SuppressWarnings("serial")
@JsonIgnoreProperties(value = { "auditSection" })
public abstract class AuditableEntity<K extends Serializable & Comparable<K>, E extends ApplicationEntity<K, ?>> extends ApplicationEntity<K, E> implements Auditable{

	@Embedded
	protected AuditSection auditSection = new AuditSection();
	
	@Override
	public AuditSection getAuditSection() {
		return this.auditSection;
	}

	@Override
	public void setAuditSection(AuditSection audit) {
		this.auditSection = audit;
	}


}
