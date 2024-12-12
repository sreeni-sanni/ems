package com.abn.ems.filter;

import com.abn.ems.auth.service.JWTAuthenticationProvider;
import com.abn.ems.auth.service.JwtUtilService;
import com.abn.ems.exception.EmsApplicationException;
import com.abn.ems.exception.JwtException;
import com.abn.ems.service.EmployeeService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static com.abn.ems.constants.Constant.*;
import static java.util.Arrays.stream;

/**
 * Filter for processing and validating JWT tokens in incoming HTTP requests.
 *
 * <p>The {@code JwtAuthFilter} class is a part of the Spring Security configuration and intercepts
 * requests to extract and validate the JWT token from the `Authorization` header. If the token is
 * valid, it populates the {@link org.springframework.security.core.context.SecurityContext} with
 * the authenticated user's details.</p>
 *
 * <h3>Key Responsibilities:</h3>
 * <ul>
 *     <li>Extracts the JWT token from the `Authorization` header.</li>
 *     <li>Validates the token using a {@link JwtUtilService} utilityService class.</li>
 *     <li>Sets the authentication details in the {@code SecurityContext} if the token is valid.</li>
 *     <li>Delegates the request to the next filter in the chain if the token is invalid or missing.</li>
 * </ul>
 */

@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtilService jwtUtilService;
    private final JWTAuthenticationProvider provider;

    private final HandlerExceptionResolver resolver;

    public JwtAuthFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,JwtUtilService jwtUtilService,JWTAuthenticationProvider provider) {
        this.resolver = resolver;
        this.jwtUtilService = jwtUtilService;
        this.provider = provider;
    }

    /**
     * Filters the incoming request to validate the JWT token.
     *
     * <p>This method intercepts each request and checks the `Authorization` header for a valid JWT token.
     * If the token is present and valid, it sets the user authentication details in the {@link org.springframework.security.core.context.SecurityContext}.
     * Otherwise, the request is allowed to proceed without authentication.</p>
     *
     * @param request     the HTTP request.
     * @param response    the HTTP response.
     * @param filterChain the filter chain to delegate the request.
     * @throws ServletException if an error occurs during request processing.
     * @throws IOException      if an I/O error occurs during request processing.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ((request.getRequestURI().startsWith(AUTH_TOKEN) || request.getRequestURI().startsWith(SWAGGER_UI) || request.getRequestURI().startsWith(SWAGGER_V3_API))) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = getTokenFromRequest(request);
            try {
                if (StringUtils.hasText(token) && jwtUtilService.validateToken(token)) {
                    Authentication authentication = provider.authenticate(new UsernamePasswordAuthenticationToken(token, token));
                    if (authentication != null) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (SignatureException | ExpiredJwtException exe) {
                if (exe instanceof SignatureException) {
                    throw new JwtException(INVALID_JWT_TOKEN);
                } else {
                    throw new JwtException(TOKEN_EXPIRED);
                }
            }
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            resolver.resolveException(request, response, null, e);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        throw new EmsApplicationException(TOKEN_MISSING);
    }
}
