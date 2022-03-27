package com.techshop.admin.security;

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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Bean
	public UserDetailsService detailsService() {
		return new TechShopDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(detailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return authenticationProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/users/**").hasAuthority("Admin")
		.antMatchers("/categories/**").hasAnyAuthority("Admin", "Editor")
		.antMatchers("/brands/**").hasAnyAuthority("Admin", "Editor")
		.antMatchers("/products/**").hasAnyAuthority("Admin", "Salesperson", "Editor", "Shipper")
		.antMatchers("/questions/**").hasAnyAuthority("Admin", "Assistant")
		.antMatchers("/reviews/**").hasAnyAuthority("Admin", "Assistant")
		.antMatchers("/customers/**").hasAnyAuthority("Admin", "Salesperson")
		.antMatchers("/shipping/**").hasAnyAuthority("Admin", "Salesperson")
		.antMatchers("/orders/**").hasAnyAuthority("Admin", "Salesperson", "Shipper")
		.antMatchers("/reports/**").hasAnyAuthority("Admin", "Salesperson")
		.antMatchers("/articles/**").hasAnyAuthority("Admin", "Editor")
		.antMatchers("/menu/**").hasAnyAuthority("Admin", "Editor")
		.antMatchers("/users/**").hasAuthority("Admin")
		.anyRequest().authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
			.usernameParameter("email")
			.passwordParameter("password")
			.permitAll()
			.and().logout().permitAll();
	}
	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
//	}
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/resources/**", "/images/**", "/js/**", "/webjars/**");
	}
}
