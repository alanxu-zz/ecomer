package me.alanx.ecomer.core.model.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import me.alanx.ecomer.core.model.generic.AuditableEntity;

@Entity
@Table(name = "PERMISSIONS")
@JsonDeserialize(using = PermissionDeserializer.class)
public class Permission extends AuditableEntity<Long, Permission> {

	private static final long serialVersionUID = 813468140197420748L;

	Permission() {}
	
	@JsonCreator
	public Permission(@JsonProperty("permission") String permissionName) {
		this.name = permissionName;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR")
	private Long id;
	
	@Column(name = "PERMISSION_NAME", nullable = false, unique = true)
	private String name;
	
	
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonProperty("permission")
	public String getPermissionName() {
		return name;
	}

	public void setPermissionName(String permissionName) {
		this.name = permissionName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Permission other = (Permission) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
}

@SuppressWarnings("serial")
class PermissionDeserializer extends StdDeserializer<Permission> {

	private Map<String, Permission> permissionCache = new HashMap<>();
	
	public PermissionDeserializer() {
		this(null);
	}
	
	public PermissionDeserializer(Class<?> vc) { 
        super(vc); 
    }

	@Override
	public Permission deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
        //String permissionName = node.get("permission").asText();
		String permissionName = node.asText();
        
        Permission p = this.permissionCache.get(permissionName);
        
        if (p == null) {
        	p = new Permission(permissionName);
        	this.permissionCache.put(permissionName, p);
        }
        
        return p;
	}
	

	
}
