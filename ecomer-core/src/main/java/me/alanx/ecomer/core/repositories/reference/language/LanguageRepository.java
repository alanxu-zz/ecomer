package me.alanx.ecomer.core.repositories.reference.language;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.reference.Language;

@CacheConfig(cacheNames = "languages")
public interface LanguageRepository extends JpaRepository <Language, Integer> {
	
	@Cacheable(key="#code")
	Language findByCode(String code) throws ServiceException;

	
	@Override
	@Cacheable(key="#page + '.' + #page.pageNumber + '.' + #page.pageSize + '.' + #page.offset + '.' + #page.sort")
	Page<Language> findAll(Pageable page);

	@Override
	@Cacheable(key="#root.methodName")
	List<Language> findAll();

	@Override
	//@Cacheable(key="#root.methodName + #sort")
	List<Language> findAll(Sort sort);
	
	

}
