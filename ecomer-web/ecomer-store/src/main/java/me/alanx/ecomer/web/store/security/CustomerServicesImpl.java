package me.alanx.ecomer.web.store.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.configuration.ApplicationConstants;
import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.auth.Group;
import me.alanx.ecomer.core.model.auth.Permission;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.services.auth.GroupService;
import me.alanx.ecomer.core.services.auth.PermissionService;
import me.alanx.ecomer.core.services.auth.SecurityDataAccessException;
import me.alanx.ecomer.core.services.customer.CustomerService;
import me.alanx.ecomer.web.constants.Constants;


/**
 * 
 * @author casams1
 *         http://stackoverflow.com/questions/5105776/spring-security-with
 *         -custom-user-details
 */
//@Service("customerDetailsService")
public class CustomerServicesImpl implements UserDetailsService{

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServicesImpl.class);
	
	public final static String ROLE_PREFIX = "ROLE_";//Spring Security 4
	
	@Inject
	private CustomerService customerService;
	

	
	@Inject
	protected PermissionService  permissionService;
	
	@Inject
	protected GroupService   groupService;
	
	
	
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {

		Customer user = null;
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		try {

				user = customerService.getByNick(userName);
			
				if(user==null) {
					return null;
				}
	
	

			GrantedAuthority role = new SimpleGrantedAuthority(ROLE_PREFIX + ApplicationConstants.PERMISSION_CUSTOMER_AUTHENTICATED);//required to login
			authorities.add(role); 
			
			List<Long> groupsId = new ArrayList<>();
			List<Group> groups = user.getGroups();
			for(Group group : groups) {
				//TODO
				groupsId.add(Long.valueOf(group.getId().toString()));
			}
			
	
			if(CollectionUtils.isNotEmpty(groupsId)) {
		    	List<Permission> permissions = permissionService.getPermissions(groupsId);
		    	for(Permission permission : permissions) {
		    		GrantedAuthority auth = new SimpleGrantedAuthority(permission.getPermissionName());
		    		authorities.add(auth);
		    	}
			}
			

			

		
		
		} catch (ServiceException e) {
			LOGGER.error("Exception while querrying customer",e);
			throw new SecurityDataAccessException("Cannot authenticate customer",e);
		}
		
		User authUser = new User(userName, user.getPassword(), true, true,
				true, true, authorities);
		
		return authUser;
		
		
	}
	




}
