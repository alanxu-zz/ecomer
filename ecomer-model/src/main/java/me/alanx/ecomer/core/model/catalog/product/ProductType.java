package me.alanx.ecomer.core.model.catalog.product;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import me.alanx.ecomer.core.annotation.FilterCandidate;
import me.alanx.ecomer.core.constants.SchemaConstant;
import me.alanx.ecomer.core.model.common.audit.AuditListener;
import me.alanx.ecomer.core.model.common.audit.AuditSection;
import me.alanx.ecomer.core.model.common.audit.Auditable;
import me.alanx.ecomer.core.model.generic.ApplicationEntity;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT_TYPE", schema=SchemaConstant.SALESMANAGER_SCHEMA)
public class ProductType extends ApplicationEntity<Long, ProductType> implements Auditable {
	private static final long serialVersionUID = 65541494628227593L;
	
	public final static String GENERAL_TYPE = "GENERAL";
	
	@Id
	@Column(name = "PRODUCT_TYPE_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRD_TYPE_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	@FilterCandidate
	private Long id;
	
	@Embedded
	private AuditSection auditSection = new AuditSection();
	
	@Column(name = "PRD_TYPE_CODE")
	private String code;
	
	@Column(name = "PRD_TYPE_ADD_TO_CART")
	private Boolean allowAddToCart;
	
	public ProductType() {
	}
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public AuditSection getAuditSection() {
		return auditSection;
	}

	@Override
	public void setAuditSection(AuditSection auditSection) {
		this.auditSection = auditSection;
	}

	public boolean isAllowAddToCart() {
		return allowAddToCart;
	}

	public void setAllowAddToCart(boolean allowAddToCart) {
		this.allowAddToCart = allowAddToCart;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}
