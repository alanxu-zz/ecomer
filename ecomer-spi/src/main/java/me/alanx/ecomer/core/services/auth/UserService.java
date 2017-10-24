package me.alanx.ecomer.core.services.auth;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.auth.User;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;



public interface UserService extends SalesManagerEntityService<Long, User> {

	User getByUserName(String userName) throws ServiceException;

	List<User> listUser() throws ServiceException;
	
	/**
	 * Create or update a User
	 * @param user
	 * @throws ServiceException
	 */
	User saveOrUpdate(User user) throws ServiceException;

	List<User> listByStore(MerchantStore store) throws ServiceException;



}
