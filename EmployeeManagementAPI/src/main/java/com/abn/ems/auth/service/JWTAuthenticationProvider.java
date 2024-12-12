package com.abn.ems.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom authentication provider for validating JSON Web Tokens (JWT) in the application.
 * <p>
 * This class integrates with Spring Security to authenticate requests based on JWTs. It
 * verifies the token, extracts claims (such as user details and roles), and creates
 * authentication objects for further use within the security framework.
 * </p>
 */
@AllArgsConstructor
@Component
public class JWTAuthenticationProvider  implements AuthenticationProvider {

    JwtUtilService jwtUtilService;

    /**
     * Authenticates the provided {@code Authentication} token by validating the JWT.
     * <p>
     * If the JWT is valid, this method extracts the user details and returns an
     * authenticated {@code Authentication} object.
     * </p>
     *
     * @param authentication the authentication request object containing the JWT.
     * @return a fully authenticated {@code Authentication} object if the JWT is valid.
     * @throws AuthenticationException if the token is invalid or the user is not found.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        String username = jwtUtilService.getUserName(token);
        String role = jwtUtilService.getRole(token);
        List<GrantedAuthority> authoritiesList = new ArrayList<GrantedAuthority>();
        authoritiesList.add(new SimpleGrantedAuthority("ROLE_" + role));
        return new UsernamePasswordAuthenticationToken(username, null, authoritiesList);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
