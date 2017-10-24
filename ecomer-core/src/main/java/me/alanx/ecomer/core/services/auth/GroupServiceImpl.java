package me.alanx.ecomer.core.services.auth;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.auth.Group;
import me.alanx.ecomer.core.model.auth.GroupType;
import me.alanx.ecomer.core.repositories.user.GroupRepository;
import me.alanx.ecomer.core.services.auth.GroupService;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;


@Service("groupService")
public class GroupServiceImpl extends
		SalesManagerEntityServiceImpl<Long, Group> implements GroupService {

	GroupRepository groupRepository;


	@Inject
	public GroupServiceImpl(GroupRepository groupRepository) {
		super(groupRepository);
		this.groupRepository = groupRepository;

	}


	@Override
	public List<Group> listGroup(GroupType groupType) throws ServiceException {
		try {
			return groupRepository.findByType(groupType);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public List<Group> listGroupByIds(Set<Integer> ids) throws ServiceException {
		try {
			return groupRepository.findByIds(ids);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


}
