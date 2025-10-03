package com.lobernar.myapp.entities;

import java.time.ZonedDateTime;

public class EventDTO {
    private Integer id;
    private String owner;  // just the username
    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    // Constructors
    public EventDTO(Integer id, String name, String ownerUsername, ZonedDateTime startDate, ZonedDateTime endDate) {
        this.id = id;
        this.owner = ownerUsername;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Default constructor
    public EventDTO() {}

    // Getters
    public Integer getId() {return id;}
    public String getOwner() {return owner;}
    public String getName() {return name;}
    public ZonedDateTime getStartDate() {return startDate;}
    public ZonedDateTime getEndDate() {return endDate;}

    // Setters
    public void setId(Integer id) {this.id = id;}
    public void setOwner(String owner) {this.owner = owner;}
    public void setName(String name) {this.name = name;}
    public void setStartDate(ZonedDateTime startDate) {this.startDate = startDate;}
    public void setEndDate(ZonedDateTime endDate) {this.endDate = endDate;}
}
