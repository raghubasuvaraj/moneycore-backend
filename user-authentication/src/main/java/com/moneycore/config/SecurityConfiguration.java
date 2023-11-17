package com.moneycore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.moneycore.filter.JwtRequestFilter;
import com.moneycore.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailService userDetailService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/**/authenticate/**").permitAll()
				.antMatchers("/**/refreshtoken/**").permitAll()
				.antMatchers("/api/clientmanagement/client/register").permitAll()
				.antMatchers("/api/clientmanagement/client/validate").permitAll()
				.antMatchers("/api/institutionmanagement/institution/signup").permitAll()
				.antMatchers("/api/walletmanagement/wallet/register").permitAll()
				.antMatchers("/api/usermanagement/user/mccs/**").permitAll()
				.antMatchers("/api/usermanagement/user/regions/**").permitAll()
				.antMatchers("/api/usermanagement/user/countries/**").permitAll()
				.antMatchers("/api/usermanagement/swagger/data/**").permitAll()
				.antMatchers("/api/usermanagement/user/cities/**").permitAll()
				.antMatchers("/api/usermanagement/user/languages/**").permitAll()
				.antMatchers("/api/clientmanagement/backoffice/**").permitAll()
				.antMatchers("/api/clientmanagement/client/passwordReset").permitAll()
				.antMatchers("/**/internal/**").permitAll()
				.antMatchers("/api/walletmanagement/client/walletid/**").permitAll()
				.antMatchers("/v2/api-docs",
						"/configuration/ui",
						"/swagger-resources/**",
						"/configuration/security",
						"/swagger-ui.html",
						"/webjars/**").permitAll()
				.antMatchers("/api").hasAnyRole("USER", "ADMIN").anyRequest().authenticated().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.inMemoryAuthentication().passwordEncoder(new
	 * BCryptPasswordEncoder()).withUser("vx2Q3XTNTamsfLjX2z4m")
	 * .password("$2a$10$vfIg3gfkA88lEcvPx287ieiR0uEsE2PJRvJPBRHzImd35dibKn47i").
	 * roles("USER").and() .withUser("tk4HTx7mfhcAnmvjVTGV")
	 * .password("$2a$10$suq5eog3IB.9ZCIZnKDXWu50heF0XcesUCjXknOCpayG4XbV.uHq2").
	 * roles("ADMIN"); }
	 * 
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * http.cors().disable();
	 * http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/api").
	 * hasAnyRole("USER", "ADMIN") .anyRequest().authenticated().and().httpBasic();
	 * http.csrf().disable(); }
	 * 
	 * @Bean public WebMvcConfigurer corsConfigurer() { return new
	 * WebMvcConfigurerAdapter() {
	 * 
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST",
	 * "PUT", "DELETE"); } }; }
	 */
}