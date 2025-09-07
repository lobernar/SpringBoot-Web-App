package com.lobernar.myapp.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lobernar.myapp.repositories.AuthRepository;
import com.lobernar.myapp.entities.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController{
    private final AuthRepository authRepo;

    public AuthController(final AuthRepository authRepo){
        this.authRepo = authRepo;
    }

    @GetMapping("/login/{id}")
    public Optional<User> getUserById(@RequestParam Long id){
        Optional<User> user = this.authRepo.findById(id);
        return user;
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> body) {
        System.out.println("Received: "+ body.toString());
        String username = body.get("username");
        String password = body.get("password");
        if(username.equals("admin") && password.equals("password")){return "Login successful";}
        else {return "Error logging in";}
    }
}