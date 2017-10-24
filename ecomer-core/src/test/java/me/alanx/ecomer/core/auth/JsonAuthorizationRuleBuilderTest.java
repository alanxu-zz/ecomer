package me.alanx.ecomer.core.auth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import me.alanx.ecomer.core.constants.SystemConstants;
import me.alanx.ecomer.core.model.auth.Group;
import me.alanx.ecomer.core.model.auth.Permission;
import me.alanx.ecomer.test.SpringJUnitTestBase;


@TestPropertySource(properties = {SystemConstants.PROPERTY_KEY_AUTHORIZATION_JSON + "=authorizations.json"})
@ContextConfiguration(classes = JsonAuthorizationRuleBuilder.class)
public class JsonAuthorizationRuleBuilderTest extends SpringJUnitTestBase {

	
	private static boolean initiatedGlobally;
	
	@Autowired
	private JsonAuthorizationRuleBuilder jsonAuthorizationRuleBuilder;
	
	@Autowired
	private Environment env;
	
	@Autowired
	static DefaultAuthorizationRuleBuilder defaultAuthorizationRuleBuilder = new DefaultAuthorizationRuleBuilder();
	
	private static final Logger log = LoggerFactory.getLogger(JsonAuthorizationRuleBuilderTest.class);

	
	@Before
	public void generateJson() throws Exception {
		if (!initiatedGlobally) {

			//some init operation
			
			initiatedGlobally = true;
		}

		
	}

	@Test
	public void testDeserialize() {
		
		Set<Group> groups = jsonAuthorizationRuleBuilder.getPermissionedGroups();
		Set<Group> pectedGroups = jsonAuthorizationRuleBuilder.getPermissionedGroups();
		Set<Permission> permissions = jsonAuthorizationRuleBuilder.getPermissions();
		Set<Permission> expectedPermissions = defaultAuthorizationRuleBuilder.getPermissions();
		
		assertNotNull(groups);
		assertEquals(groups.size(), defaultAuthorizationRuleBuilder.getPermissionedGroups().size());

		for(Group g : groups) {
			assertTrue(g instanceof Group);
			log.info("g {}", g);
			assertTrue(pectedGroups.contains(g)); 
		}
		
		log.info("permission size: {}, expected size: {}", permissions.size(), defaultAuthorizationRuleBuilder.getPermissions().size());
		
		assertTrue(permissions.size() == expectedPermissions.size());
		
		
	}
}
