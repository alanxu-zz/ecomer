package me.alanx.ecomer.core.services.auth;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.auth.Group;
import me.alanx.ecomer.core.model.auth.Permission;
import me.alanx.ecomer.core.model.auth.PermissionCriteria;
import me.alanx.ecomer.core.model.auth.PermissionList;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;



public interface PermissionService extends SalesManagerEntityService<Long, Permission> {
	
	public final static String PERMISSION_AUTHENTICATED = "AUTH";
	
	public final static String PERMISSION_CUSTOMER_AUTHENTICATED = "AUTH_CUSTOMER";
	
	List<Permission> getByName();

	List<Permission> listPermission()  throws ServiceException;

	Permission getById(Long permissionId);


//	void deletePermission(Permission permission) throws ServiceException;

	List<Permission> getPermissions(List<Long> groupIds) throws ServiceException;

	void deletePermission(Permission permission) throws ServiceException;

	PermissionList listByCriteria(PermissionCriteria criteria) throws ServiceException ;

	void removePermission(Permission permission, Group group) throws ServiceException;

}
