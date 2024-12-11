package com.abn.ems.filter;

import com.abn.ems.Enums.Role;
import com.abn.ems.auth.JWTAuthenticationProvider;
import com.abn.ems.auth.JwtUtilService;
import com.abn.ems.exception.EmsApplicationException;
import com.abn.ems.model.EmployeeResponse;
import com.abn.ems.service.EmployeeService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.abn.ems.constant.Constant.*;

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
 *     <li>Validates the token using a {@link com.abn.ems.auth.JwtUtilService} utilityService class.</li>
 *     <li>Sets the authentication details in the {@code SecurityContext} if the token is valid.</li>
 *     <li>Delegates the request to the next filter in the chain if the token is invalid or missing.</li>
 * </ul>
 */
@AllArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtUtilService jwtUtilService;
    private EmployeeService employeeService;
    private JWTAuthenticationProvider provider;

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


        if (AUTH_TOKEN.equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = getTokenFromRequest(request);
        if (StringUtils.hasText(token) && jwtUtilService.validateToken(token)) {
            Authentication authentication = provider.authenticate( new UsernamePasswordAuthenticationToken(token, token));
            if(authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        throw new EmsApplicationException(TOKEN_MISSING);
    }
}
