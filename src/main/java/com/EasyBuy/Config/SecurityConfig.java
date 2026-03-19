package com.EasyBuy.Config;

import com.EasyBuy.Security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.info("Inside SecurityFilterChain ...");
		http
				// Disable CSRF for REST APIs
				.csrf(csrf -> csrf.disable())

				// Make it stateless (no sessions)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// Authorization rules
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/index.html", "/*.js", "/*.css", "/*.ico", "/*.png", "/auth/**",
								"/login")
						.permitAll()
						// ── Role-protected REST endpoints ──
						.requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/manager/**")
						.hasAnyRole("ADMIN", "MANAGER").requestMatchers("/user/**")
						.hasAnyRole("ADMIN", "MANAGER", "USER")
						// ── Everything else needs a JWT ──
						.anyRequest().authenticated())

				// Add JWT filter
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

				// Disable default login mechanisms
				.formLogin(form -> form.disable()).httpBasic(basic -> basic.disable());

		return http.build();
	}

	// Password encoder bean
	@Bean
	public PasswordEncoder passwordEncoder() {
		log.info("Inside PasswordEncoder ...");
		return new BCryptPasswordEncoder();
	}
}