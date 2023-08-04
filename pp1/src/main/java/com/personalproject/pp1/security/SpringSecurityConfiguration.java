package com.personalproject.pp1.security;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//		1. authenticate all  requests

		http.authorizeHttpRequests(auth -> auth.requestMatchers("/").permitAll()
				.requestMatchers(HttpMethod.POST, "/users").permitAll().anyRequest().authenticated());

//		2. if not authenticated then show a web page

		http.httpBasic(withDefaults());

// 3. enable csrf -> post and put

		http.csrf(csrf -> csrf.disable());

//		4. username and pass from db

		return http.build();
//	

	}

}
