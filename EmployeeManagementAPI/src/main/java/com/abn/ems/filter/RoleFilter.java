package com.abn.ems.filter;

import com.abn.ems.Enums.Role;
import com.abn.ems.exception.RoleNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
public class RoleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String role = request.getHeader("role");
        if(role !=null  && (role.length()>=3 && role.length()<=50)) {
            Role.valueOf(role.toUpperCase());
        }else{
            throw new RoleNotFoundException("Invalid role");
        }
        filterChain.doFilter(request, response);
    }
}
