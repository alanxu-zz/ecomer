package me.alanx.ecomer.core.model.catalog.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import me.alanx.ecomer.core.annotation.FilterCandidate;
import me.alanx.ecomer.core.model.generic.ApplicationEntity;

@Entity
@Table(name = "PRODUCT_ATTRIBUTE")
public class ProductAttribute extends ApplicationEntity<Long, ProductAttribute>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8562818034805171463L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR")
	private Long id;
	
	@NotNull
	@Column(name = "PRODUCT_ATTRIBUTE_NAME", nullable = false, unique = true)
	private String name;
	
	@Column(name = "PRODUCT_ATTRIBUTE_DESC")
	private String description;
	
	@Enumerated
	@Column(name = "PRODUCT_ATTRIBUTE_TYPE")
	private ProductAttributeType type;
	
	protected ProductAttribute() {}

	
	public ProductAttribute(String name, String description, ProductAttributeType type) {
		super();
		this.name = name;
		this.description = description;
		this.type = type;
	}



	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the type
	 */
	public ProductAttributeType getType() {
		return type;
	}
	
}
