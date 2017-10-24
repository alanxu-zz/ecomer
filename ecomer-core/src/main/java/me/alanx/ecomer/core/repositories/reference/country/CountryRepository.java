package me.alanx.ecomer.core.repositories.reference.country;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import me.alanx.ecomer.core.model.reference.Country;

public interface CountryRepository extends JpaRepository <Country, Integer> {
	
	@Cacheable("countriesByCode")
	@Query("select c from Country c left join fetch c.descriptions cd where c.isoCode=?1")
	Country findByIsoCode(String code);
	
	@Cacheable("countriesById")
	@Query("select c from Country c left join fetch c.descriptions cd where cd.language.id=?1")
	List<Country> listByLanguage(Integer id);

}
