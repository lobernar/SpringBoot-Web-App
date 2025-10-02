package com.lobernar.myapp.entities;

import java.time.LocalDateTime;

public class EventDTO {
    private Integer id;
    private String owner;  // just the username
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Constructors
    public EventDTO(Integer id, String name, String ownerUsername, LocalDateTime startDate, LocalDateTime endDate) {
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
    public LocalDateTime getStartDate() {return startDate;}
    public LocalDateTime getEndDate() {return endDate;}

    // Setters
    public void setId(Integer id) {this.id = id;}
    public void setOwner(String owner) {this.owner = owner;}
    public void setName(String name) {this.name = name;}
    public void setStartDate(LocalDateTime startDate) {this.startDate = startDate;}
    public void setEndDate(LocalDateTime endDate) {this.endDate = endDate;}
}
