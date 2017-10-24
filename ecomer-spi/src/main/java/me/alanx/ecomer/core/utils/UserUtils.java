package me.alanx.ecomer.core.utils;

import java.util.Collection;

import me.alanx.ecomer.core.model.auth.Group;
import me.alanx.ecomer.core.model.auth.User;

public class UserUtils {
	
	public static boolean userInGroup(User user,String groupName) {
		
		Collection<Group> logedInUserGroups = user.getGroups();
		for(Group group : logedInUserGroups) {
			if(group.getGroupName().equals(groupName)) {
				return true;
			}
		}
		
		return false;
		
	}

}
