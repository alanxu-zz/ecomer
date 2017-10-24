package me.alanx.ecomer.core.auth;

import java.util.Set;

import me.alanx.ecomer.core.model.auth.Group;
import me.alanx.ecomer.core.model.auth.Permission;

/**
 * Not thread safe.
 * 
 * @author alanxu
 *
 */
public interface AuthorizationRuleBuilder {
	
	Set<Permission> getPermissions();
	
	Set<Group> getPermissionedGroups();

}
