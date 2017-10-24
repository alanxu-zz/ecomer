package me.alanx.ecomer.core.repositories.user;

import me.alanx.ecomer.core.model.auth.PermissionCriteria;
import me.alanx.ecomer.core.model.auth.PermissionList;




public interface PermissionRepositoryCustom {

	PermissionList listByCriteria(PermissionCriteria criteria);


}
