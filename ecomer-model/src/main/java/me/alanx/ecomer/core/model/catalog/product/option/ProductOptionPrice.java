package me.alanx.ecomer.core.model.catalog.product.option;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import me.alanx.ecomer.core.constants.SchemaConstant;
import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.generic.ApplicationEntity;

@Entity
@Table(name="PRODUCT_OPTION_PRICE", schema=SchemaConstant.SALESMANAGER_SCHEMA,
	uniqueConstraints={
		@UniqueConstraint(columnNames={
				"OPTION_ID",
				"OPTION_VALUE_ID",
				"PRODUCT_ID"
			})
	}
)
public class ProductOptionPrice extends ApplicationEntity<Long, ProductOptionPrice> {
	private static final long serialVersionUID = -6537491946539803265L;
	
	@Id
	@Column(name = "PRODUCT_OPTION_PRICE_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_ATTR_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	
	@Column(name="PRODUCT_ATRIBUTE_PRICE")
	private BigDecimal optionPrice;


	@Column(name="PRODUCT_OPTION_PRICE_SORT_ORD")
	private Integer productOptionSortOrder;
	
	@Column(name="PRODUCT_OPTION_PRICE_FREE")
	private boolean isFree;
	

	@Column(name="PRODUCT_OPTION_PRICE_WEIGHT")
	private BigDecimal weight;
	
	@Column(name="PRODUCT_OPTION_PRICE_DEFAULT")
	private boolean isDefault=false;
	
	@Column(name="PRODUCT_OPTION_PRICE_REQUIRED")
	private boolean required=false;
	
	/**
	 * a read only attribute is considered as a core attribute addition
	 */
	@Column(name="PRODUCT_OPTION_PRICE_FOR_DISP")
	private boolean displayOnly=false;
	

	@Column(name="PRODUCT_OPTION_PRICE_DISCOUNTED")
	private boolean discounted=false;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="OPTION_ID", nullable=false)
	private ProductOption productOption;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="OPTION_VALUE_ID", nullable=false)
	private ProductOptionValue productOptionValue;
	
	
	/**
	 * This transient object property
	 * is a utility used only to submit from a free text
	 */
	@Transient
	private String price = "0";
	
	
	/**
	 * This transient object property
	 * is a utility used only to submit from a free text
	 */
	@Transient
	private String sortOrder = "0";
	


	/**
	 * This transient object property
	 * is a utility used only to submit from a free text
	 */
	@Transient
	private String additionalWeight = "0";
	
	public String getAttributePrice() {
		return price;
	}

	public void setAttributePrice(String attributePrice) {
		this.price = attributePrice;
	}

	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private Product product;
	
	public ProductOptionPrice() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}



	public Integer getProductOptionSortOrder() {
		return productOptionSortOrder;
	}

	public void setProductOptionSortOrder(Integer productOptionSortOrder) {
		this.productOptionSortOrder = productOptionSortOrder;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setIsFree(boolean isFree) {
		this.isFree = isFree;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isDisplayOnly() {
		return displayOnly;
	}

	public void setDisplayOnly(boolean displayOnly) {
		this.displayOnly = displayOnly;
	}

	public boolean isDiscounted() {
		return discounted;
	}

	public void setDiscounted(boolean discounted) {
		this.discounted = discounted;
	}

	public ProductOption getProductOption() {
		return productOption;
	}

	public void setProductOption(ProductOption productOption) {
		this.productOption = productOption;
	}

	public ProductOptionValue getProductOptionValue() {
		return productOptionValue;
	}

	public void setProductOptionValue(ProductOptionValue productOptionValue) {
		this.productOptionValue = productOptionValue;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	public String getAttributeSortOrder() {
		return sortOrder;
	}

	public void setAttributeSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getAdditionalWeight() {
		return additionalWeight;
	}

	public void setAdditionalWeight(String additionalWeight) {
		this.additionalWeight = additionalWeight;
	}
	
	public BigDecimal getOptionPrice() {
		return optionPrice;
	}

	public void setOptionPrice(BigDecimal optionPrice) {
		this.optionPrice = optionPrice;
	}



}
