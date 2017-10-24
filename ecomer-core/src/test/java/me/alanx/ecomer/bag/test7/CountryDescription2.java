package me.alanx.ecomer.bag.test7;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class CountryDescription2 {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR")
	private Long id;
	
	
	
	
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


	
}
