package com.saml.dox365.core.app.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId("dox365");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/auth/**").permitAll()
				.antMatchers("**/swagger-ui.html").permitAll()
				.antMatchers("/oauth/**").permitAll()
				.antMatchers("/api/core/**").permitAll()
				.antMatchers("/api/org/**").permitAll()
				.antMatchers("/api/license/**").permitAll()
				.antMatchers("/api/department/**").permitAll()
				.antMatchers("/api/template/**").access("hasAuthority('USER')")
				.antMatchers("/api/users/**").access("hasAuthority('ADMIN')").anyRequest().authenticated();
		
		/* access("hasAuthority('ADMIN')")
		 * http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.
		 * STATELESS).and().antMatcher("/**")
		 * .authorizeRequests().antMatchers("/api/signin**").permitAll().antMatchers(
		 * "/swagger-ui.html**") .permitAll().antMatchers("/",
		 * "/login**").permitAll().antMatchers("/api/signin/**").permitAll()
		 * .antMatchers("/api/template**").hasAuthority("ADMIN").antMatchers(
		 * "/api/core**") .hasAnyAuthority("ADMIN",
		 * "USER").antMatchers("/api/**").authenticated().anyRequest().authenticated();
		 */
	}

	/*
	 * @Override public void configure(HttpSecurity http) throws Exception {
	 * http.authorizeRequests() .antMatchers("/swagger-ui.html").permitAll(); }
	 */

	/*
	 * @Override public void configure(ResourceServerSecurityConfigurer resources) {
	 * resources.resourceId("api"); }
	 * 
	 * 
	 * @Override public void configure(HttpSecurity http) throws Exception {
	 * http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.
	 * STATELESS).and().antMatcher("/**")
	 * .authorizeRequests().antMatchers("/api/signin**").permitAll().antMatchers(
	 * "/swagger-ui.html**") .permitAll().antMatchers("/",
	 * "/login**").permitAll().antMatchers("/api/signin/**").permitAll().antMatchers
	 * ("/api/template**")
	 * .hasAuthority("ADMIN").antMatchers("/api/core**").hasAnyAuthority("ADMIN",
	 * "USER") .antMatchers("/api/**").authenticated().anyRequest().authenticated();
	 * }
	 */

}
