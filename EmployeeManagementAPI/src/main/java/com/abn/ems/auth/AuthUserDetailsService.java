package com.abn.ems.auth;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

//@Service
public class AuthUserDetailsService implements UserDetailsService {

    String user="admin";
    String password="admin";
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  new User(user,
                password, new ArrayList<>());
    }
}
