package me.alanx.ecomer.core.services.common.generic;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.generic.ApplicationEntity;

/**
 * @param <T> entity type
 */
public abstract class SalesManagerEntityServiceImpl<K extends Serializable & Comparable<K>, E extends ApplicationEntity<K, ?>>
	implements SalesManagerEntityService<K, E> {
	
	/**
	 * Classe de l'entité, déterminé à partir des paramètres generics.
	 */
	private Class<E> objectClass;


    private JpaRepository<E, K> repository;

	@SuppressWarnings("unchecked")
	public SalesManagerEntityServiceImpl(JpaRepository<E, K> repository) {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.objectClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
		this.repository = repository;
	}
	
	protected final Class<E> getObjectClass() {
		return objectClass;
	}


	public E getById(K id) {
		return repository.findOne(id);
	}

	
	public E save(E entity) throws ServiceException {
		return repository.saveAndFlush(entity);
	}
	
	@Override
	public List<E> save(Iterable<E> entities) throws ServiceException {
		 List<E> entityList = repository.save(entities);
		 repository.flush();
		 return entityList;
	}
	
	public E create(E entity) throws ServiceException {
		return save(entity);
	}

	
	
	public E update(E entity) throws ServiceException {
		return save(entity);
	}
	

	public void delete(E entity) throws ServiceException {
		repository.delete(entity);
	}
	
	
	public void flush() {
		repository.flush();
	}
	

	
	public List<E> list() {
		return repository.findAll();
	}
	

	public Long count() {
		return repository.count();
	}

}