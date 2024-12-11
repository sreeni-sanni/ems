package com.abn.ems.security;

import com.abn.ems.auth.JWTAuthenticationProvider;
import com.abn.ems.filter.JwtAuthFilter;
import com.abn.ems.filter.RoleFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class that configures Spring Security for the application.
 *
 * <p>This class configures HTTP security, authentication mechanisms, and authorization rules
 * for the application. It defines the necessary beans for user authentication
 * and secure HTTP endpoint access. The configuration ensures that users can access resources
 * based on their roles and permission levels.</p>
 */

@AllArgsConstructor
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    JwtAuthFilter jwtAuthFilter;

    JWTAuthenticationProvider jwtAuthenticationProvider;

    /**
     * Configures HTTP security, including enabling JWT-based authentication .
     *
     * <p>This method configures how HTTP requests are authorized and authenticated. It restricts access to
     * certain URLs based on user roles and sets up mechanisms for authentication (e.g., JWT filters).</p>
     *
     * @param http the HTTP security object used for configuring security
     * @throws Exception if an error occurs while configuring HTTP security
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(AUTH_WHITELIST).permitAll()
                                .anyRequest().authenticated())
                .authenticationProvider(jwtAuthenticationProvider)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    /**
     * Provides the authentication manager for the application.
     *
     * <p>This method is used to provide the authentication manager required by Spring Security for authenticating users.</p>
     *
     * @return the authentication manager
     * @throws Exception if an error occurs while building the authentication manager
     */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private static final String[] AUTH_WHITELIST = {"/auth/token",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/actuator/**"};
}
