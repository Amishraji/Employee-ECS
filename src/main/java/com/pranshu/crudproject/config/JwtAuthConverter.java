package com.pranshu.crudproject.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;



@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> roles = extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, roles);
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        if (jwt.getClaim("realm_access") != null) {
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            
            // Convert the 'roles' claim using ObjectMapper
            List<String> keycloakRoles = (List<String>) realmAccess.get("roles");
            
            // Create a list of GrantedAuthority objects from Keycloak roles
            List<GrantedAuthority> roles = new ArrayList<>();
            for (String keycloakRole : keycloakRoles) {
                roles.add(new SimpleGrantedAuthority("ROLE_" + keycloakRole));
            }
            
            return roles;
        }
        
        return new ArrayList<>();
    }
}