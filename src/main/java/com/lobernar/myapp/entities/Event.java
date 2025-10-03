package com.lobernar.myapp.entities;


import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User owner; // username

    @Column
    private String name; // event name

    @Column
    private ZonedDateTime startDate;

    @Column
    private ZonedDateTime endDate;

    // Constructors
    public Event(){};
    public Event(User owner, String name, ZonedDateTime sd, ZonedDateTime ed){
        this.owner = owner;
        this.name = name;
        this.startDate = sd;
        this.endDate = ed;
    }



    // Getters
    public Integer getId() {return this.id;}
    public User getOwner() {return this.owner;}
    public String getName() {return this.name;}
    public ZonedDateTime getStartDate() {return this.startDate;}
    public ZonedDateTime getEndDate() {return this.endDate;}

    // Setters
    public void setId(Integer id) {this.id=id;}
    public void setOwner(User o) {this.owner=o;}
    public void setName(String n) {this.name=n;}
    public void setStartDate(ZonedDateTime sd) {this.startDate=sd;}
    public void setEndDate(ZonedDateTime ed) {this.endDate=ed;}
}
