package com.lobernar.myapp.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.lobernar.myapp.config.JwtUtils;
import com.lobernar.myapp.entities.User;
import com.lobernar.myapp.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller for handling user-related API endpoints.
 * Provides endpoints for retrieving, updating, and deleting the authenticated user.
 */

@RestController
@RequestMapping("/api/users/")
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    /**
     * Constructor for UserController. Injects UserService and JwtUtils.
     * @param us UserService instance
     * @param ju JwtUtils instance
     */
    public UserController(final UserService us, final JwtUtils ju){
        this.userService = us;
        this.jwtUtils = ju;
    }

    /**
     * Retrieves the currently authenticated user's details.
     * @return ResponseEntity containing the User or UNAUTHORIZED status
     */
    @GetMapping("me/")
    public ResponseEntity<User> getUser(){
        User user = this.userService.getUser();
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Updates the currently authenticated user's details.
     * @param body Map containing fields to update
     * @return ResponseEntity with new JWT or UNAUTHORIZED status
     */
    @PutMapping("me/edit")
    public ResponseEntity<?> updateUser(@RequestBody Map<String, String> body){
        User updatedUser = this.userService.updateUser(body);
        if(updatedUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Generate new token with updated username if it was changed
        String newToken = this.jwtUtils.createToken(updatedUser.getId());
        return ResponseEntity.ok(Map.of("jwt", newToken));
    }

    /**
     * Deletes the currently authenticated user.
     * @param userName Username of the user to delete
     * @return ResponseEntity with OK or UNAUTHORIZED status
     */
    @DeleteMapping("me/delete")
    public ResponseEntity<?> deleteUser(String userName){
        User deletedUser = this.userService.deleteUser(userName);
        if(deletedUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }
}
