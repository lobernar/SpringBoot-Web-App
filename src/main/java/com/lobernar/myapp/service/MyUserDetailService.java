package com.lobernar.myapp.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.lobernar.myapp.entities.User;
import com.lobernar.myapp.repositories.UserRepository;

@Component
public class MyUserDetailService implements UserDetailsService{

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MyUserDetailService(UserRepository ur, PasswordEncoder pe){
        this.userRepo = ur;
        this.passwordEncoder = pe;
    }

    // Load user details by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = this.userRepo.findByUsername(username);

        if(!optUser.isPresent() || optUser.get() == null){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        User user = optUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }

    /*
     *  Add any additional methods for registering or managing users
     */
    public String addUser(User user){
        // Encrypt password before saving
        String encPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encPass);
        this.userRepo.save(user);
        return "User added successfully";
    }
    
}
