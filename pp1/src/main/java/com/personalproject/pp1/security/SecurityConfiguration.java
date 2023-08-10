package com.personalproject.pp1.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http

				.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
						.requestMatchers(HttpMethod.GET, "/").permitAll()
						.requestMatchers(HttpMethod.POST, "/login").permitAll()
						.requestMatchers(HttpMethod.GET, "/users/*").hasRole("user")
						.requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("user")
						.requestMatchers(HttpMethod.GET, "/users").hasRole("admin")
						.requestMatchers(HttpMethod.POST, "/createuser").permitAll()
						.requestMatchers(HttpMethod.POST, "/users/*/booking").hasRole("user")
						.requestMatchers(HttpMethod.GET, "/booking").hasRole("admin").anyRequest().authenticated()

				).csrf(csrf -> csrf.disable()).httpBasic(Customizer.withDefaults())

				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

}
