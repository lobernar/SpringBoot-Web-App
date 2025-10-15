package com.lobernar.myapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class representing a user in the database.
 * Maps to the 'users' table and contains user details such as username, password, and email.
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

    @Column(nullable = false)
    private String role;

    /**
     * Default constructor for JPA.
     */
    protected User(){}

    /**
     * Constructs a User with the specified details. Sets the default role to "USER".
     * @param username the username
     * @param password the hashed password
     * @param firstName the first name
     * @param lastName the last name
     * @param email the email address
     */
    public User(String username, String password, String firstName, String lastName, String email){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = "USER";
    }

    /**
     * Gets the unique identifier for the user.
     * @return the user ID
     */
    public Integer getId(){return this.id;}

    /**
     * Gets the username of the user.
     * @return the username
     */
    public String getUsername(){return this.username;}

    /**
     * Gets the hashed password of the user.
     * @return the password
     */
    public String getPassword(){return this.password;}

    /**
     * Gets the first name of the user.
     * @return the first name
     */
    public String getFirstName(){return this.firstName;}

    /**
     * Gets the last name of the user.
     * @return the last name
     */
    public String getLastName(){return this.lastName;}

    /**
     * Gets the email address of the user.
     * @return the email address
     */
    public String getEmail(){return this.email;}

    /**
     * Gets the role of the user.
     * @return the role
     */
    public String getRole(){return this.role;}

    /**
     * Sets the unique identifier for the user.
     * @param id the user ID
     */
    public void setId(Integer id){this.id=id;}

    /**
     * Sets the username of the user.
     * @param u the username
     */
    public void setUsername(String u){this.username=u;}

    /**
     * Sets the hashed password of the user.
     * @param p the password
     */
    public void setPassword(String p){this.password=p;}

    /**
     * Sets the first name of the user.
     * @param fn the first name
     */
    public void setFirstName(String fn){this.firstName=fn;}

    /**
     * Sets the last name of the user.
     * @param ln the last name
     */
    public void setLastName(String ln){this.lastName=ln;}

    /**
     * Sets the email address of the user.
     * @param e the email address
     */
    public void setEmail(String e){this.email=e;}

    /**
     * Sets the role of the user.
     * @param r the role
     */
    public void setRole(String r){this.role=r;}
}