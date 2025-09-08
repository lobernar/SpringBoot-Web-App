package com.lobernar.myapp.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lobernar.myapp.repositories.AuthRepository;
import com.lobernar.myapp.entities.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController{

    @Autowired
    private final AuthRepository authRepo;

    public AuthController(final AuthRepository authRepo){
        this.authRepo = authRepo;
    }

    @GetMapping("/users")
    public Iterable<User> getAllUsers(){
        Iterable<User> users = authRepo.findAll();
        for(User u : users){System.out.println(u.getUsername());}
        return users;
    }

    @PostMapping("/login")
    public Boolean login(@RequestBody Map<String, String> body) {
        // Deserialize JSON
        String username = body.get("username");
        String password = body.get("password");
        Optional<User> optUser = this.authRepo.findByUsername(username);
        // Check if user exists in DB
        if(!optUser.isPresent()){return false;}
        // Check if username-password match
        User user = optUser.get();
        if(user != null && user.getPassword().equals(password)){return true;}
        else {return false;}
    }

    @PostMapping("/signup")
    public Boolean signup(@RequestBody Map<String, String> body){
        User newUser = new User(body.get("username"), body.get("password"));
        // Check that no two users have the same username
        Optional<User> optUser = this.authRepo.findByUsername(body.get("username"));
        if(optUser.isPresent()) {return false;}
        // Save new user to the DB
        this.authRepo.save(newUser);
        return true;
    }
}