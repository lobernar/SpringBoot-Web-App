package com.lobernar.myapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.lobernar.myapp.filter.JwtAuthFilter;
import com.lobernar.myapp.repositories.UserRepository;
import com.lobernar.myapp.service.UserDetailService;

/*
 *  Disables CSRF & Basic Auth.
 *  Allows authentication-free access to /api/auth/**.
 *  Requires ROLE_USER for /api/user/**.
 *  Adds JWT filter for request validation.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepo;
    private final JwtAuthFilter jwtFilter;
    private final UserDetailService userDetailServ;

    public SecurityConfig(final UserRepository ur, final JwtAuthFilter jaf, final UserDetailService uds){
        this.userRepo = ur;
        this.jwtFilter = jaf;
        this.userDetailServ = uds;
    }



    
}
