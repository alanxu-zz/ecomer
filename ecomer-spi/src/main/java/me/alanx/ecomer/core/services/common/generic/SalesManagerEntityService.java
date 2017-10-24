package me.alanx.ecomer.core.services.common.generic;

import java.io.Serializable;
import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;


/**
 * <p>Service racine pour la gestion des entités.</p>
 *
 * @param <T> type d'entité
 */
public interface SalesManagerEntityService<K extends Serializable & Comparable<K>, E extends me.alanx.ecomer.core.model.generic.ApplicationEntity<K, ?>> extends TransactionalAspectAwareService{

	/**
	 * Crée l'entité dans la base de données. Mis à part dans les tests pour faire des sauvegardes simples, utiliser
	 * create() car il est possible qu'il y ait des listeners sur la création d'une entité.
	 * 
	 * @param entity entité
	 */
	E save(E entity) throws ServiceException;
	
	/**
	 * Met à jour l'entité dans la base de données.
	 * 
	 * @param entity entité
	 */
	E update(E entity) throws ServiceException;
	
	/**
	 * Crée l'entité dans la base de données.
	 * 
	 * @param entity entité
	 */
	E create(E entity) throws ServiceException;

	/**
	 * Supprime l'entité de la base de données
	 * 
	 * @param entity entité
	 */
	void delete(E entity) throws ServiceException;
	

	/**
	 * Retourne une entité à partir de son id.
	 * 
	 * @param id identifiant
	 * @return entité
	 */
	E getById(K id);
	
	/**
	 * Renvoie la liste de l'ensemble des entités de ce type.
	 * 
	 * @return liste d'entités
	 */
	List<E> list();
	
	
	/**
	 * Compte le nombre d'entités de ce type présentes dans la base.
	 * 
	 * @return nombre d'entités
	 */
	Long count();
	
	/**
	 * Flushe la session.
	 */
	void flush();

	/**
	 * Save multiple entities
	 * 
	 * @param entities
	 * @return
	 * @throws ServiceException
	 */
	List<E> save(Iterable<E> entities) throws ServiceException;
	


}
