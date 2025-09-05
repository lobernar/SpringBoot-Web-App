package com.lobernar.myapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserEntity{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;


    // Getters
    public Long getId(){return this.id;}
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}

    // Setters
    public void setId(Long id){this.id=id;}
    public void setUsername(String u){this.username=u;}
    public void setPassword(String p){this.password=p;}
}