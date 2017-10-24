package me.alanx.ecomer.core.model.catalog.product.option;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import me.alanx.ecomer.core.constants.SchemaConstant;
import me.alanx.ecomer.core.model.common.Description;

@Entity
@Table(name = "PRODUCT_OPTION_VALUE_DESCRIPTION", schema=SchemaConstant.SALESMANAGER_SCHEMA, uniqueConstraints={
	@UniqueConstraint(columnNames={
			"PRODUCT_OPTION_VALUE_ID",
			"LANGUAGE_ID"
		})
	}
)
public class ProductOptionValueDescription extends Description {
	private static final long serialVersionUID = 7402155175956813576L;
	
	@ManyToOne(targetEntity = ProductOptionValue.class)
	@JoinColumn(name = "PRODUCT_OPTION_VALUE_ID")
	private ProductOptionValue productOptionValue;
	
	public ProductOptionValueDescription() {
	}

	public ProductOptionValue getProductOptionValue() {
		return productOptionValue;
	}

	public void setProductOptionValue(ProductOptionValue productOptionValue) {
		this.productOptionValue = productOptionValue;
	}

}
