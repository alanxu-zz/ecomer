package me.alanx.ecomer.core.services.auth;

import java.util.List;
import java.util.Set;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.auth.Group;
import me.alanx.ecomer.core.model.auth.GroupType;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;


public interface GroupService extends SalesManagerEntityService<Long, Group> {
	
	public final static String GROUP_ADMIN = "ADMIN";
	public final static String GROUP_SUPERADMIN = "SUPERADMIN";
	public final static String GROUP_CUSTOMER = "CUSTOMER";
	public final static String ANONYMOUS_CUSTOMER = "ANONYMOUS_CUSTOMER";

	List<Group> listGroup(GroupType groupType) throws ServiceException;
	List<Group> listGroupByIds(Set<Integer> ids) throws ServiceException;

}
