package me.alanx.ecomer.core.services.auth;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.auth.Group;
import me.alanx.ecomer.core.model.auth.Permission;
import me.alanx.ecomer.core.model.auth.PermissionCriteria;
import me.alanx.ecomer.core.model.auth.PermissionList;
import me.alanx.ecomer.core.repositories.user.PermissionRepository;
import me.alanx.ecomer.core.services.auth.PermissionService;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;



@Service("permissionService")
public class PermissionServiceImpl extends
		SalesManagerEntityServiceImpl<Long, Permission> implements
		PermissionService {

	private PermissionRepository permissionRepository;


	@Inject
	public PermissionServiceImpl(PermissionRepository permissionRepository) {
		super(permissionRepository);
		this.permissionRepository = permissionRepository;

	}

	@Override
	public List<Permission> getByName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Permission getById(Long permissionId) {
		return permissionRepository.findOne(permissionId);

	}


	@Override
	public void deletePermission(Permission permission) throws ServiceException {
		permission = this.getById(permission.getId());//Prevents detached entity error
		//permission.setGroups(null);
		
		this.delete(permission);
	}
	

	@Override
	public List<Permission> getPermissions(List<Long> groupIds)
			throws ServiceException {
		Set<Long> ids = new HashSet<>(groupIds);
		return permissionRepository.findByGroups(ids);
	}

	@Override
	public PermissionList listByCriteria(PermissionCriteria criteria)
			throws ServiceException {
		return permissionRepository.listByCriteria(criteria);
	}

	@Override
	public void removePermission(Permission permission,Group group) throws ServiceException {
		permission = this.getById(permission.getId());//Prevents detached entity error
	
		//permission.getGroups().remove(group);
		

	}

	@Override
	public List<Permission> listPermission() throws ServiceException {
		return permissionRepository.findAll();
	}



}
