package me.alanx.ecomer.core.repositories.user;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import me.alanx.ecomer.core.model.auth.Group;
import me.alanx.ecomer.core.model.auth.GroupType;

public interface GroupRepository extends JpaRepository<Group, Long> {


	Group findById(Long id);
	
	@Query("select distinct g from Group as g join fetch g.permissions perms order by g.id")
	List<Group> findAll();
	
	@Query("select distinct g from Group as g join fetch g.permissions perms where perms.id in (?1) ")
	List<Group> findByPermissions(Set<Integer> permissionIds);
	
	@Query("select distinct g from Group as g join fetch g.permissions perms where g.id in (?1) ")
	List<Group> findByIds(Set<Integer> groupIds);
	
	@Query("select distinct g from Group as g join fetch g.permissions perms where g.groupType = ?1")
	List<Group> findByType(GroupType type);
}
