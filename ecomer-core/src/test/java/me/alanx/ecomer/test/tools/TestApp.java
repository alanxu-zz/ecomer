package me.alanx.ecomer.test.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

import me.alanx.ecomer.core.auth.DefaultAuthorizationRuleBuilder;
import me.alanx.ecomer.core.model.auth.Group;
import me.alanx.ecomer.core.model.auth.GroupType;
import me.alanx.ecomer.core.model.auth.Permission;

public class TestApp {

	public static void main(String[] args) {
		
		DefaultAuthorizationRuleBuilder authRuleBuilder = new DefaultAuthorizationRuleBuilder();
		
		ObjectMapper mapper = new ObjectMapper()
									.setSerializationInclusion(Include.NON_EMPTY)
									.configure(SerializationFeature.INDENT_OUTPUT, true);
		
		
		
		try {
			System.out.println(System.getProperty("user.dir"));
			File jsonFile = new File("src/main/resources/authorizations.json");
			jsonFile.getParentFile().mkdirs();
			jsonFile.createNewFile();
			mapper.writeValue(System.out, authRuleBuilder.getPermissionedGroups());
			mapper.writeValue(jsonFile, authRuleBuilder.getPermissionedGroups());
		} catch (JsonGenerationException e) {
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

}
