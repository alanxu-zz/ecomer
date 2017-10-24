package me.alanx.ecomer.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.javafaker.Faker;

@ActiveProfiles({"test", "init"})
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class, DBTestConfig.class, MockConfig.class})
public abstract class SpringJUnitTestBase {
	
	private Faker faker = new Faker();
	
	@Autowired
	private ApplicationContext appContext;
	
	protected ApplicationContext getApplicationContext() {
		return this.appContext;
	}
	
	protected Faker fake() {
		return this.faker;
	}
	
}
