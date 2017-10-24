package me.alanx.ecomer.core.model.auth;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import me.alanx.ecomer.core.model.common.audit.Auditable;
import me.alanx.ecomer.core.model.generic.AuditableEntity;


@Entity
@Table(name = "EC_GROUPS")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="name", scope=Group.class)
public class Group extends AuditableEntity<Long, Group> implements Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3786127652573709701L;

	Group() {}
	
	@JsonCreator
	public Group(@JsonProperty("name")String groupName) {
		this.groupName = groupName;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR")
	private Long id;
	
	@Column(name = "GROUP_TYPE", nullable = false)
	@JsonProperty("type")
	private GroupType groupType;
	
	@Column(name = "GROUP_NAME", nullable = false, unique = true)
	@JsonProperty("name")
	private String groupName;
	
	@ManyToMany
	@JoinTable(name = "GROUP_PERMISSIONS",
				joinColumns = {@JoinColumn(name = "GROUP_ID")},
				inverseJoinColumns = {@JoinColumn(name = "PERMISSOIN_ID")}
			)
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="permission")
	@JsonIdentityReference(alwaysAsId = true)
	private Set<Permission> permissions = new HashSet<Permission>();	
	
	
	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}


	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setGroupType(GroupType groupType) {
		this.groupType = groupType;
	}

	public GroupType getGroupType() {
		return groupType;
	}

	public Group addPermission(Permission permission) {
		this.permissions.add(permission);
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result + ((groupType == null) ? 0 : groupType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		//result = prime * result + ((permissions == null) ? 0 : permissions.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		if (groupType != other.groupType)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (permissions == null) {
			if (other.permissions != null)
				return false;
		} else if (other.permissions == null) {
			return false;
		} else {
			for (Permission p : permissions) {
				if (!other.permissions.contains(p)) {
					return false;
				}
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Group [id=" + id + ", groupType=" + groupType + ", groupName=" + groupName + ", permissions="
				+ permissions + "]";
	}
	
	

}
