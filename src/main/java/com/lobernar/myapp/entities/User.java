package com.lobernar.myapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/* 
    Defines the User table in the database. 
*/

@Entity
@Table(name="users")
public class User{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable =  false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;


    // Default Constructor
    protected User(){}
    // Constructor used when creating User to be saved in DB
    public User(String username, String password, String firstName, String lastName, String email){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Getters
    public Integer getId(){return this.id;}
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    public String getFirstName(){return this.firstName;}
    public String getLastName(){return this.lastName;}
    public String getEmail(){return this.email;}

    // Setters
    public void setId(Integer id){this.id=id;}
    public void setUsername(String u){this.username=u;}
    public void setPassword(String p){this.password=p;}
    public void setFirstName(String fn){this.firstName=fn;}
    public void setLastName(String ln){this.lastName=ln;}
    public void setEmail(String e){this.email=e;}
}