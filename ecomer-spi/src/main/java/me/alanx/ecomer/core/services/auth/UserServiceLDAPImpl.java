package me.alanx.ecomer.core.services.auth;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.auth.User;
import me.alanx.ecomer.core.model.merchant.MerchantStore;



public class UserServiceLDAPImpl implements UserService {

	@Override
	public User save(User entity) throws ServiceException {
		throw new ServiceException("Not implemented");

	}

	@Override
	public User update(User entity) throws ServiceException {
		throw new ServiceException("Not implemented");

	}

	@Override
	public User create(User entity) throws ServiceException {
		throw new ServiceException("Not implemented");

	}

	@Override
	public void delete(User entity) throws ServiceException {
		throw new ServiceException("Not implemented");

	}



	@Override
	public User getById(Long id) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public List<User> list() {
		throw new UnsupportedOperationException("Not implemented");
	}



	@Override
	public Long count() {
		throw new UnsupportedOperationException("Not implemented");
	}





	@Override
	public User getByUserName(String userName) throws ServiceException {
		throw new ServiceException(new UnsupportedOperationException("Not implemented"));
	}

	@Override
	public List<User> listUser() throws ServiceException {
		throw new ServiceException(new UnsupportedOperationException("Not implemented"));
	}

	@Override
	public User saveOrUpdate(User user) throws ServiceException {
		throw new ServiceException(new UnsupportedOperationException("Not implemented"));

	}

	@Override
	public List<User> listByStore(MerchantStore store)
			throws ServiceException {
		throw new ServiceException(new UnsupportedOperationException("Not implemented"));
	}

	/* (non-Javadoc)
	 * @see com.salesmanager.core.business.services.common.generic.SalesManagerEntityService#flush()
	 */
	@Override
	public void flush() {
		throw new UnsupportedOperationException();		
	}

	@Override
	public List<User> save(Iterable<User> entities) throws ServiceException {
		throw new ServiceException(new UnsupportedOperationException("Not implemented"));
	}



}
