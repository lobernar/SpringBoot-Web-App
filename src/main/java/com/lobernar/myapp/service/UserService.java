package com.lobernar.myapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lobernar.myapp.entities.User;
import com.lobernar.myapp.repositories.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepo;
    private final PasswordEncoder passEncoder;

    @Autowired
    public UserService(UserRepository ur, PasswordEncoder pe){
        this.userRepo = ur;
        this.passEncoder = pe;
    }

    public Optional<User> findByUsername(String username){
        return this.userRepo.findByUsername(username);
    }

    public User addUser(User user){
        user.setPassword(passEncoder.encode(user.getPassword()));
        return this.userRepo.save(user);
    }

    public User updateUser(User user){
        Optional<User> optUser = this.userRepo.findByUsername(user.getUsername());
        if(!optUser.isPresent()) {
            System.out.println("User not present");
            return null;
        }
        User userToUpdate = optUser.get();
            // TODO: Check that no 2 users share the same username + email
            System.out.println("Updating " + user.getUsername());
            System.out.println("Username: " + user.getUsername());
            if(user.getFirstName() != null){userToUpdate.setFirstName(user.getFirstName());}
            if(user.getLastName() != null) {userToUpdate.setLastName(user.getLastName());}
            if(user.getEmail() != null) {userToUpdate.setEmail(user.getEmail());}
            if(user.getUsername() != null) {userToUpdate.setUsername(user.getUsername());}
            if(user.getPassword() != null) {userToUpdate.setPassword(user.getPassword());}
            return this.userRepo.save(userToUpdate);
    }
    
}
