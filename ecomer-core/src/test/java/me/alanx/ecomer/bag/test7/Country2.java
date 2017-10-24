package me.alanx.ecomer.bag.test7;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import me.alanx.ecomer.core.constants.SchemaConstant;
import me.alanx.ecomer.core.model.generic.ApplicationEntity;

@Entity
public class Country2  {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR")
	private Integer id;
	
	@OneToMany(/*mappedBy = "country", */cascade = CascadeType.ALL)
	private List<CountryDescription2> descriptions = new ArrayList<CountryDescription2>();
	

	
	@Transient
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public List<CountryDescription2> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<CountryDescription2> descriptions) {
		this.descriptions = descriptions;
	}


}
