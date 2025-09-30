package com.lobernar.myapp.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lobernar.myapp.service.UserService;
import com.lobernar.myapp.config.JwtUtils;
import com.lobernar.myapp.entities.User;

/*
 *  Handles user registration and login, generating JWT tokens. 
 *  Register (/register)
 *  Encrypts the password.
 *  Saves the user in the database.
 *  Returns a JWT token.
 *  Login (/login)
 *  Authenticates the user.
 *  Generates and returns a JWT token.
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController{

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;
    private final UserService userService;

    @Autowired
    public AuthController(JwtUtils ju, AuthenticationManager am, UserService us){
        this.jwtUtils = ju;
        this.authManager = am;
        this.userService = us;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        // Deserialize JSON
        String username = body.get("username");
        String password = body.get("password");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            Authentication auth = this.authManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = jwtUtils.createToken(username);
            return ResponseEntity.ok(Map.of("jwt", token));
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> body){
        String username = body.get("username");
        String password = body.get("password");
        String firstName = body.get("firstName");
        String lastName = body.get("lastName");
        String email = body.get("email");
        
        // Encode Password and add user to User Repository
        User newUser = new User(username, password, firstName, lastName, email);
        this.userService.addUser(newUser);

        // Generate JWT
        String token = this.jwtUtils.createToken(username);

        return ResponseEntity.ok(Map.of("jwt", token));
    }
}