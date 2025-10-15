package com.lobernar.myapp.entities;

import java.time.ZonedDateTime;

/**
 * Data Transfer Object (DTO) for transferring event data between layers.
 * Contains event details such as id, owner username, name, and dates.
 */
public class EventDTO {

    private Integer id;

    private String owner;

    private String name;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;


    /**
     * Constructs an EventDTO with the specified details.
     * @param id the event ID
     * @param name the event name
     * @param ownerUsername the username of the event owner
     * @param startDate the start date and time
     * @param endDate the end date and time
     */
    public EventDTO(Integer id, String name, String ownerUsername, ZonedDateTime startDate, ZonedDateTime endDate) {
        this.id = id;
        this.owner = ownerUsername;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Default constructor.
     */
    public EventDTO() {}

    /**
     * Gets the unique identifier for the event.
     * @return the event ID
     */
    public Integer getId() {return id;}

    /**
     * Gets the username of the event owner.
     * @return the owner's username
     */
    public String getOwner() {return owner;}

    /**
     * Gets the name of the event.
     * @return the event name
     */
    public String getName() {return name;}

    /**
     * Gets the start date and time of the event.
     * @return the start date and time
     */
    public ZonedDateTime getStartDate() {return startDate;}

    /**
     * Gets the end date and time of the event.
     * @return the end date and time
     */
    public ZonedDateTime getEndDate() {return endDate;}

    /**
     * Sets the unique identifier for the event.
     * @param id the event ID
     */
    public void setId(Integer id) {this.id = id;}

    /**
     * Sets the username of the event owner.
     * @param owner the owner's username
     */
    public void setOwner(String owner) {this.owner = owner;}

    /**
     * Sets the name of the event.
     * @param name the event name
     */
    public void setName(String name) {this.name = name;}

    /**
     * Sets the start date and time of the event.
     * @param startDate the start date and time
     */
    public void setStartDate(ZonedDateTime startDate) {this.startDate = startDate;}

    /**
     * Sets the end date and time of the event.
     * @param endDate the end date and time
     */
    public void setEndDate(ZonedDateTime endDate) {this.endDate = endDate;}
}
