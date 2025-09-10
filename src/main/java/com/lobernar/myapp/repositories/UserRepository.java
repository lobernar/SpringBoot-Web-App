package com.lobernar.myapp.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.lobernar.myapp.entities.User;

public interface UserRepository extends CrudRepository<User, Integer>{
    Optional<User> findByUsername(String username);
}
