package com.pranshu.crudproject.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  
	private JwtAuthConverter jwtAuthConverter;


	public SecurityConfig( JwtAuthConverter jwtAuthConverter) {
    	this.jwtAuthConverter=jwtAuthConverter;
    	
    }
	
	    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        
        http.authorizeHttpRequests(authorize -> authorize
        		.requestMatchers(HttpMethod.GET, "/employees").permitAll()
        		.requestMatchers(HttpMethod.GET, "/clientcredentials").permitAll()
             
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(t -> 
                t.jwt(configurer -> configurer.jwtAuthenticationConverter(jwtAuthConverter)));
           
        
        http.sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return http.build();
    }

   
    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation("https://keycloak.route53testdemo.fun/realms/oauth");
    }
    
}

