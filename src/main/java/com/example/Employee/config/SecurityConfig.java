package com.example.Employee.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.example.Employee.filter.JwtFilter;
import com.example.Employee.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	
	@Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
                return userRepository.findByEmailId(userEmail);
                //.orElseThrow(()->new UsernameNotFoundException(("user with "+userEmail+" not found")));
            }
        };
    }
	
	@Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticator = new DaoAuthenticationProvider();
        authenticator.setPasswordEncoder(passwordEncoder());
        authenticator.setUserDetailsService(userDetailsService());
        return authenticator;
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
	
	 @Bean
	 public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
	        return config.getAuthenticationManager();
	    }
	 
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		 http.csrf().disable()
		    .authorizeHttpRequests()
		        .requestMatchers(
		        		 "/api/v1/users/authenticate",
		            "/api/v1/employees",
		            "/api/v1/users/validateUser",
		            "/register",
		            "/*",
		            "/api/v1/users",		         
		            "/hello",
		            "/api/v1/employees/employeesForUser"
		        )
		        .permitAll()
		        .anyRequest().authenticated().and()
		        .authenticationProvider(authenticationProvider())
		        .sessionManagement()
		        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		        .and()
		    .cors().configurationSource(request -> {
		        var cors = new CorsConfiguration();
		        cors.setAllowedOrigins(List.of("*"));
		        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		        cors.setAllowedHeaders(List.of("*"));
		        cors.setExposedHeaders(List.of("*"));
		        return cors;
		    });

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	  
	}

}

