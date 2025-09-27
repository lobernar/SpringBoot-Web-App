package com.lobernar.myapp.entities;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/* 
    Defines the Event table in the database. 
*/

@Entity
@Table(name="events")
public class Event {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String owner; // username

    @Column
    private String name; // event name

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    // Constructors
    public Event(){};
    public Event(String owner, String name, LocalDateTime sd, LocalDateTime ed){
        this.owner = owner;
        this.name = name;
        this.startDate = sd;
        this.endDate = ed;
    }



    // Getters
    public Integer getId() {return this.id;}
    public String getOwner() {return this.owner;}
    public String getName() {return this.name;}
    public LocalDateTime getStartDate() {return this.startDate;}
    public LocalDateTime getEndDate() {return this.endDate;}

    // Setters
    public void setId(Integer id) {this.id=id;}
    public void setOwner(String o) {this.owner=o;}
    public void setName(String n) {this.name=n;}
    public void setStartDate(LocalDateTime sd) {this.startDate=sd;}
    public void setEndDate(LocalDateTime ed) {this.endDate=ed;}
}
