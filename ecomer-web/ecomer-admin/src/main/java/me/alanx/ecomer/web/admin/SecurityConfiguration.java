package me.alanx.ecomer.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/admin/api/**").authenticated()
				.and()
			.httpBasic()
				.and()
			.authorizeRequests()
				.antMatchers("/resources/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/admin/logon.html").permitAll()
				.loginProcessingUrl("/login").permitAll()
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/admin")
				.and()
			.logout()
				.logoutUrl("/admin/logout")
				.logoutSuccessUrl("/admin")
				.invalidateHttpSession(true)
				.and()
			.rememberMe()
				.and()
			.csrf()
				.disable()
			.headers()
				.defaultsDisabled()
				.cacheControl();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder);
	}
	
	

}