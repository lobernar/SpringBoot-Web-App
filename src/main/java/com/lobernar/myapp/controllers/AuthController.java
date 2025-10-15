package com.lobernar.myapp.controllers;

import java.util.Map;
import java.util.Optional;

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

/**
 * Controller for handling authentication endpoints (login and signup).
 * Handles user registration and login, generating JWT tokens.
 * Register (/signup): Encrypts the password, saves the user, returns a JWT token.
 * Login (/login): Authenticates the user, generates and returns a JWT token.
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController{

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;
    private final UserService userService;

    /**
     * Constructor for AuthController. Injects JwtUtils, AuthenticationManager, and UserService.
     * @param ju JwtUtils instance
     * @param am AuthenticationManager instance
     * @param us UserService instance
     */
    @Autowired
    public AuthController(JwtUtils ju, AuthenticationManager am, UserService us){
        this.jwtUtils = ju;
        this.authManager = am;
        this.userService = us;
    }

    /**
     * Authenticates a user and returns a JWT token if successful.
     * @param body Map containing username and password
     * @return ResponseEntity with JWT token or error message
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        // Deserialize JSON
        String username = body.get("username");
        String password = body.get("password");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            Authentication auth = this.authManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            // Generate JWT
            Optional<User> optUser = this.userService.findByUsername(username);
            if(!optUser.isPresent()) {
                System.out.println("User not present");
                return null;
            }
            User user = optUser.get();
            String token = jwtUtils.createToken(user.getId());
            return ResponseEntity.ok(Map.of("jwt", token));
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Registers a new user, encodes their password, saves them, and returns a JWT token.
     * @param body Map containing user registration fields
     * @return ResponseEntity with JWT token
     */
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
        String token = this.jwtUtils.createToken(newUser.getId());

        return ResponseEntity.ok(Map.of("jwt", token));
    }
}