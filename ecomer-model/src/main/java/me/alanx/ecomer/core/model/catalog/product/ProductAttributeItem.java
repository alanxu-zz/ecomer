package me.alanx.ecomer.core.model.catalog.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import me.alanx.ecomer.core.model.generic.ApplicationEntity;

@Entity
@Table(name = "PRODUCT_ATTRIBUTE_ITEM")
public class ProductAttributeItem extends ApplicationEntity<Long, ProductAttributeItem>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6870171079389861524L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ATTRIBUTE_ID", nullable = false)
	private ProductAttribute attribute;
	
	@Column(name = "ATTRIBUTE_VALUE")
	private String value;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private Product product;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the attribute
	 */
	public ProductAttribute getAttribute() {
		return attribute;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}
	
	
}
