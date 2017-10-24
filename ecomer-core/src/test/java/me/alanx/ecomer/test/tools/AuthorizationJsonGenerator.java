package me.alanx.ecomer.test.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import me.alanx.ecomer.core.auth.DefaultAuthorizationRuleBuilder;
import me.alanx.ecomer.core.model.auth.Group;

public class AuthorizationJsonGenerator {
	public void generate(Set<Group> groups, String file) throws Exception {
		ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(Include.NON_EMPTY)
				.configure(SerializationFeature.INDENT_OUTPUT, true);


		File jsonFile = new File(file);
		jsonFile.getParentFile().mkdirs();
		jsonFile.createNewFile();
		mapper.writeValue(System.out, groups);
		mapper.writeValue(new FileOutputStream(jsonFile), groups);
	}
	
	public static void main(String[] args) {
		AuthorizationJsonGenerator g = new AuthorizationJsonGenerator();
		DefaultAuthorizationRuleBuilder authRuleBuilder = new DefaultAuthorizationRuleBuilder();
		try {
			g.generate(authRuleBuilder.getPermissionedGroups(), "src/test/resources/authorizations.json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
