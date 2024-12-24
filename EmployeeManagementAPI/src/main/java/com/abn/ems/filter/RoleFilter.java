package com.abn.ems.filter;

import com.abn.ems.enums.Role;
import com.abn.ems.exception.RoleNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static com.abn.ems.constants.Constant.*;

/**
 * A role filter for validating the "Role" header in incoming HTTP requests.
 * <p>
 * This filter ensures that a valid role is present in the request header. If the
 * header is missing or contains an invalid role , the filter prevents further processing
 * and returns an appropriate error response to the client.
 * </p>
 */

@Component
@Slf4j
public class RoleFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;

    public RoleFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * Performs validation of the "Role" header in the HTTP request.
     * <p>
     * If the "Role" header is missing or invalid, this method sets an error
     * response and terminates further processing of the request.
     * </p>
     *
     * @param request     the {@code ServletRequest} object.
     * @param response    the {@code ServletResponse} object.
     * @param filterChain the {@code FilterChain} to pass the request to the next filter.
     * @throws IOException      if an I/O error occurs during request processing.
     * @throws ServletException if an error occurs during request processing.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if ((request.getRequestURI().startsWith(AUTH_TOKEN) || request.getRequestURI().startsWith(SWAGGER_UI) || request.getRequestURI().startsWith(SWAGGER_V3_API))) {
            filterChain.doFilter(request, response);
            return;
        }
        String role = request.getHeader(ROLE);
        try {
            if (role != null && (role.length() > 3 && role.length() < 50) && Role.isValidRole(role)) {
                filterChain.doFilter(request, response);
            } else {
                throw new RoleNotFoundException(ROLE_MISSING);
            }
        } catch (RuntimeException | ServletException e) {
            resolver.resolveException(request, response, null, e);
        }
    }
}
