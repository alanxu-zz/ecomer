package me.alanx.ecomer.core.auth;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import me.alanx.ecomer.core.constants.SystemConstants;
import me.alanx.ecomer.core.model.auth.Group;
import me.alanx.ecomer.core.model.auth.Permission;

@Profile("init")
@Component
public class JsonAuthorizationRuleBuilder implements AuthorizationRuleBuilder {

	private Set<Group> groups;
	
	private Set<Permission> permissions = new HashSet<>();
	
	private String jsonURI;
	
	private static final Logger log = LoggerFactory.getLogger(JsonAuthorizationRuleBuilder.class);

	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private ApplicationContext appCtx;

	@Autowired
	public JsonAuthorizationRuleBuilder(@Value("${"+SystemConstants.PROPERTY_KEY_AUTHORIZATION_JSON+"}")String jsonURI) {
		this.jsonURI = jsonURI;
	}
	
	@PostConstruct
	private void init() {
		
		log.debug("Loading autorization configuratin from location: {}", jsonURI);
		
        try {
        	File authorizationJson = resourceLoader.getResource(jsonURI).getFile();
            /*ObjectMapper jacksonObjectMapper = new ObjectMapper();
			groups = jacksonObjectMapper.readValue(authorizationJson,new TypeReference<Set<Group>>() {});*/
        	
        	ObjectReader jacksonObjectMapper = new ObjectMapper()
        											.readerFor(new TypeReference<Set<Group>>() {})
        											.with(DeserializationFeature.EAGER_DESERIALIZER_FETCH);
			groups = jacksonObjectMapper.readValue(authorizationJson);
			
			for(Group g : groups) {
				Set<Permission> pSet = g.getPermissions();
				if (pSet == null)
					continue;
				for (Permission p : pSet) {
					this.permissions.add(p);
				}
			}
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public Set<Permission> getPermissions() {
		return this.permissions;
	}

	@Override
	public Set<Group> getPermissionedGroups() {
		return this.groups;
	}
	
}
