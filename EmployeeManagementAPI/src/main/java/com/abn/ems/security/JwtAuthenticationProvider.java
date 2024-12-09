package com.abn.ems.security;

import com.abn.ems.auth.JwtUtilService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private JwtUtilService jwtUtilService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String token = authentication.getCredentials().toString();
    if(!jwtUtilService.validateToken(token)) {
        throw new BadCredentialsException("Invalid token");
    }
        String username = jwtUtilService.getUserName(token);
        List<GrantedAuthority> authoritiesList = new ArrayList<GrantedAuthority>();
        authoritiesList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return  new UsernamePasswordAuthenticationToken(username, token,authoritiesList);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
