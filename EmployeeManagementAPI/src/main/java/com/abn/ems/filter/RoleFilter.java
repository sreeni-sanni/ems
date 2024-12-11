package com.abn.ems.filter;

import com.abn.ems.Enums.Role;
import com.abn.ems.exception.RoleNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static com.abn.ems.constant.Constant.*;

/**
 * A filter that checks whether the incoming HTTP request contains a valid role header.
 * If the role is missing or invalid, the request will be rejected .
 */

@Component
@Slf4j
public class RoleFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;

    public RoleFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (AUTH_TOKEN.equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        String role = request.getHeader(ROLE);
        try {
            if (role != null && (role.length() >= 3 && role.length() <= 50) && Role.isValidRole(role)) {
                Role.valueOf(role.toUpperCase());
            } else {
                throw new RoleNotFoundException(ROLE_MISSING);
            }
            filterChain.doFilter(request, response);
        } catch (RuntimeException | ServletException e) {
            resolver.resolveException(request, response, null, e);
            ;
        }
    }
}
