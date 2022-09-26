package com.ropvatika.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
/*@Bean
	public PasswordEncoder PasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
*/
	@Bean
	public UserDetailsService userDetailsService() {
		return new RopvatikaUserDetailsService();
	}
	
	/*@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests().anyRequest().permitAll();
	}*/
	
    @Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticatioProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.authenticationProvider(authenticatioProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/users/**").hasAuthority("Admin")
		.antMatchers("/categories/**").hasAnyAuthority("Admin","Editor")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		
		.loginPage("/login")
		.usernameParameter("email")
		.permitAll()
		.and().logout().permitAll()
		.and().rememberMe()
		.key("AbcDefghijKlmnOpqrs_1234567890")
		.tokenValiditySeconds(7 * 24 * 60 * 60);
		
	}


	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**","/js/**","/webjars/**");
	}

	
	
}