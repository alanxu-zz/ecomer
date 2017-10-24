package me.alanx.ecomer.core.repositories.user;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import me.alanx.ecomer.core.model.auth.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>, PermissionRepositoryCustom {

	
	@Query("select p from Permission as p where p.id = ?1")
	Permission findOne(Long id);
	
	@Query("select p from Permission as p order by p.id")
	List<Permission> findAll();
	
	//@Query("select distinct p from Permission as p join fetch p.groups groups where groups.id in (?1)")
	@Query("select distinct g.permissions from Group as g where g.id in (?1)")
	List<Permission> findByGroups(Set<Long> groupIds);
}
