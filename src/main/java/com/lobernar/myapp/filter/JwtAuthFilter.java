/**
 * Filter for JWT authentication in the Spring Security filter chain.
 * Extracts and validates JWT tokens from the Authorization header of incoming requests.
 * Sets the authentication in the SecurityContext if the token is valid.
 */
package com.lobernar.myapp.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lobernar.myapp.config.JwtUtils;
import com.lobernar.myapp.service.MyUserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * JwtAuthFilter integrates JWT authentication into the Spring Security filter chain.
 * It intercepts requests, extracts and validates JWT tokens, and sets authentication context for valid tokens.
 */

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private MyUserDetailService myUserDetailService;
    private JwtUtils jwtUtils;

    /**
     * Constructor for JwtAuthFilter. Injects MyUserDetailService and JwtUtils.
     * @param muds MyUserDetailService instance
     * @param ju JwtUtils instance
     */
    @Autowired
    public JwtAuthFilter(MyUserDetailService muds, JwtUtils ju){
        this.myUserDetailService = muds;
        this.jwtUtils = ju;
    }

    /**
     * Filters incoming HTTP requests to extract and validate JWT tokens.
     * If a valid token is found, sets the authentication in the SecurityContext.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain)
    throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        Integer userId = null;
        String jwt = null;

        // Extract userId from JWT using JwtUtils
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            jwt = authHeader.substring(7); // JWT begins at index 7
            userId = jwtUtils.extractUserId(jwt);
        }

        // If userId is present and no authentication is set, validate the token and set authentication
        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = myUserDetailService.loadUserById(userId);
            if(jwtUtils.validateToken(jwt, userId)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, 
                    null, 
                    userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
