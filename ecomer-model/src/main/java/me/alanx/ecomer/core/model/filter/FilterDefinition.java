package me.alanx.ecomer.core.model.filter;

import java.util.Collection;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import me.alanx.ecomer.core.model.catalog.category.Category;

@Entity
@Table(name = "FILTER_DEFINITION")
public class FilterDefinition {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR")
	private Long id;
	
	@ManyToMany
	@JoinTable(name = "CATEGORY_FILTERS",
		joinColumns = @JoinColumn(name = "CATEGORY_ID"),
		inverseJoinColumns = @JoinColumn(name = "FILTER_ID")
		)
	private Collection<Category> categories;
	
	@Column(name = "FILTER_VALUE_TYPE", nullable = false)
	@NotNull
	@Enumerated(EnumType.STRING)
	private FilterValueType type = FilterValueType.UNDEFINED;
	
	@Column(name = "FILTER_MODE")
	@NotNull
	@Enumerated(EnumType.STRING)
	private FilterValueMode mode = FilterValueMode.UNDEFINED;
	
	@ElementCollection
	@CollectionTable(name = "FILTER_OPTION_LABELS",
		joinColumns = @JoinColumn(name = "FILTER_DEFINITION_ID")
			)
	@OrderColumn(name = "FILTER_OPTION_LABEL_POS")
	@Column(name = "FILTER_OPTION_LABEL")
	private Collection<String> optionLabels;
	
	@ElementCollection
	@CollectionTable(name = "FILTER_OPTION_VALUES",
		joinColumns = @JoinColumn(name = "FILTER_DEFINITION_ID")
			)
	@OrderColumn(name = "FILTER_OPTION_VALUE_POS")
	@Column(name = "FILTER_OPTION_VALUE")
	private Collection<String> optionValues;
	
	@Column(name = "FILTER_LABEL", nullable = false)
	@NotNull(message = "{validation.filter-definition.label.notnull}")
	private String label;
	
	@Column(name = "FILTER_KEY")
	//@NotNull
	private String key;
	
	@Column(name = "IS_DEFAULT")
	private boolean isDefault;
	

	public FilterDefinition() {
		super();
	}
	
	public FilterDefinition(String key) {
		super();
		this.key = key;
	}
	
	public FilterDefinition(String key, FilterValueType type, boolean isCollection) {
		super();
		this.type = type;
		this.key = key;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the category
	 */
	public Collection<Category> getCategories() {
		return categories;
	}
	/**
	 * @param category the category to set
	 */
	public void Category(Collection<Category> categories) {
		this.categories = categories;
	}
	/**
	 * @return the type
	 */
	public FilterValueType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(FilterValueType type) {
		this.type = type;
	}
	/**
	 * @return the mode
	 */
	public FilterValueMode getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public void setMode(FilterValueMode mode) {
		this.mode = mode;
	}
	/**
	 * @return the filterOptionLabels
	 */
	public Collection<String> getOptionLabels() {
		return optionLabels;
	}
	/**
	 * @param filterOptionLabels the filterOptionLabels to set
	 */
	public void setOptionLabels(Collection<String> filterOptionLabels) {
		this.optionLabels = filterOptionLabels;
	}
	/**
	 * @return the filterOptionValues
	 */
	public Collection<String> getOptionValues() {
		return optionValues;
	}
	/**
	 * @param filterOptionValues the filterOptionValues to set
	 */
	public void setOptionValues(Collection<String> filterOptionValues) {
		this.optionValues = filterOptionValues;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the isDefault
	 */
	public boolean isDefault() {
		return isDefault;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	
}
