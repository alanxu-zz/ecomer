package me.alanx.ecomer.core.repositories.reference.zone;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import me.alanx.ecomer.core.model.reference.Zone;

@CacheConfig(cacheNames = "zones")
public interface ZoneRepository extends JpaRepository<Zone, Long> {
	
	@Cacheable(key = "'CODE_' + #code")
	Zone findByCode(String code);
	
	@Cacheable(key = "'ID_' + #id")
	@Query("select z from Zone z left join fetch z.descriptions zd where zd.language.id=?1")
	List<Zone> listByLanguage(Integer id);
	
	@Cacheable(key = "'LANG_' + #languageId + '_AND_' + #isoCode")
	@Query("select z from Zone z left join fetch z.descriptions zd join fetch z.country zc where zc.isoCode=?1 and zd.language.id=?2")
	List<Zone> listByLanguageAndCountry(String isoCode, Integer languageId);

}
