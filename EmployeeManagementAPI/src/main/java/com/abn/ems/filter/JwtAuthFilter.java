package com.abn.ems.filter;

import com.abn.ems.Enums.Role;
import com.abn.ems.auth.JwtUtilService;
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

@AllArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtUtilService jwtUtilService;
    private EmployeeService employeeService;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token=getTokenFromRequest(request);
        if(StringUtils.hasText(token) && jwtUtilService.validateToken(token)){

            String username = jwtUtilService.getUserName(token);

           EmployeeResponse employee=employeeService.getUser(username);
            List<GrantedAuthority> authoritiesList = new ArrayList<GrantedAuthority>();
            authoritiesList.add(new SimpleGrantedAuthority("ROLE_"+Role.getRole(employee.roleId())));
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null,authoritiesList);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
       filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
