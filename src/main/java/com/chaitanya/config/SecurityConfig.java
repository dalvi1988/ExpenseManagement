package com.chaitanya.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@ComponentScan({ "com.chaitanya.*" })
public class SecurityConfig {

	@Autowired
	@Qualifier("loginDetailsService")
	UserDetailsService loginDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginDetailsService).passwordEncoder(passwordEncoder());
	}

	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> auth.requestMatchers("/login/**", "/theme/**").permitAll()
				.requestMatchers("/approveFromEmail/**").permitAll().requestMatchers("/master/**")
				.hasAuthority("hasRole('ADMIN_ROLE')").requestMatchers("/mis/**").hasAuthority("hasRole('ADMIN_ROLE')")
				// .antMatchers("/approvalflow/**").access("hasRole('SUPER_ADMIN')")
				.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").failureUrl("/login?error").usernameParameter("username")
						.passwordParameter("password").permitAll())
				.logout(logout -> logout.logoutSuccessUrl("/login?logout")).csrf(csrf -> csrf.disable())
				.exceptionHandling(exception -> exception.accessDeniedPage("/others/403"));
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}