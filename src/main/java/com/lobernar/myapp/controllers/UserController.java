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

/* 
    Retrieves user details based on the authenticated token. 
*/

@RestController
@RequestMapping("/api/users/")
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public UserController(final UserService us, final JwtUtils ju){
        this.userService = us;
        this.jwtUtils = ju;
    }

    @GetMapping("me/")
    public ResponseEntity<User> getUser(){
        User user = this.userService.getUser();
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(user);
    }

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

    @DeleteMapping("me/delete")
    public ResponseEntity<?> deleteUser(String userName){
        User deletedUser = this.userService.deleteUser(userName);
        if(deletedUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }
}
