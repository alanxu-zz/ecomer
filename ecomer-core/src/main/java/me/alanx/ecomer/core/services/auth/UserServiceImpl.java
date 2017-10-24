package me.alanx.ecomer.core.services.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.configuration.ApplicationConstants;
import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.auth.Group;
import me.alanx.ecomer.core.model.auth.GroupType;
import me.alanx.ecomer.core.model.auth.Permission;
import me.alanx.ecomer.core.model.auth.User;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.repositories.user.UserRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;
import me.alanx.ecomer.core.services.merchant.MerchantStoreService;
import me.alanx.ecomer.core.services.system.EmailService;

@Service("userService")
public class UserServiceImpl extends SalesManagerEntityServiceImpl<Long, User> implements UserService, UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private static final String DEFAULT_INITIAL_PASSWORD = "password";

	@Inject
	private UserService userService;

	@Inject
	private MerchantStoreService merchantStoreService;

	@Inject
	@Named("passwordEncoder")
	private PasswordEncoder passwordEncoder;

	@Inject
	protected PermissionService permissionService;

	@Inject
	protected GroupService groupService;

	public final static String ROLE_PREFIX = "ROLE_";// Spring Security 4
	@Autowired
	private UserRepository userRepository;

	@Inject
	public UserServiceImpl(UserRepository userRepository) {
		super(userRepository);
		this.userRepository = userRepository;

	}

	@Inject
	private EmailService emailService;

	@Override
	public User getByUserName(String userName) throws ServiceException {

		return userRepository.findByUserName(userName);

	}

	@Override
	public void delete(User user) throws ServiceException {

		User u = this.getById(user.getId());
		super.delete(u);

	}

	@Override
	public List<User> listUser() throws ServiceException {
		try {
			return userRepository.findAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<User> listByStore(MerchantStore store) throws ServiceException {
		try {
			return userRepository.findByStore(store.getId());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public User saveOrUpdate(User user) throws ServiceException {

		return userRepository.save(user);

	}

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {

		me.alanx.ecomer.core.model.auth.User user = null;
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		try {

			user = getByUserName(userName);

			if (user == null) {
				throw new UsernameNotFoundException(String.format("No user with name %s. ", userName));
			}

			GrantedAuthority role = new SimpleGrantedAuthority(
					ROLE_PREFIX + ApplicationConstants.PERMISSION_AUTHENTICATED);// required
																					// to
																					// login
			authorities.add(role);

			List<Long> groupsId = new ArrayList<Long>();
			Collection<Group> groups = user.getGroups();
			for (Group group : groups) {

				// TODO
				groupsId.add(group.getId());

			}

			List<Permission> permissions = permissionService.getPermissions(groupsId);
			for (Permission permission : permissions) {
				GrantedAuthority auth = new SimpleGrantedAuthority(ROLE_PREFIX + permission.getPermissionName());
				authorities.add(auth);
			}

		} catch (Exception e) {
			log.error("Exception while querrying user", e);
			throw new SecurityDataAccessException("Exception while querrying user", e);
		}

		org.springframework.security.core.userdetails.User secUser = new org.springframework.security.core.userdetails.User(
				userName, user.getAdminPassword(), user.isActive(), true, true, true, authorities);
		return secUser;
	}
	
	public void createDefaultAdmin() throws Exception {
		
		  //TODO create all groups and permissions
		
		  MerchantStore store = merchantStoreService.getMerchantStore(MerchantStore.DEFAULT_STORE);

		  String password = passwordEncoder.encode(DEFAULT_INITIAL_PASSWORD);
		  
		  List<Group> groups = groupService.listGroup(GroupType.ADMIN);
		  
		  //creation of the super admin admin:password)
		  me.alanx.ecomer.core.model.auth.User user = new me.alanx.ecomer.core.model.auth.User("admin","admin@ecomer.com");
		  user.setPassword(password);
		  user.setFirstName("Administrator");
		  user.setLastName("User");
		  
		  for(Group group : groups) {
			  if(group.getGroupName().equals(ApplicationConstants.GROUP_SUPERADMIN) || group.getGroupName().equals(ApplicationConstants.GROUP_ADMIN)) {
				  user.getGroups().add(group);
			  }
		  }

		  user.setMerchantStore(store);		  
		  userService.create(user);
		
		
	}

}
