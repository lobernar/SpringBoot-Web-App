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

/**
 * Entity class representing an event in the database.
 * Maps to the 'events' table and contains event details such as owner, name, and dates.
 */

@Entity
@Table(name="events")
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User owner;

    @Column
    private String name;

    @Column
    private ZonedDateTime startDate;

    @Column
    private ZonedDateTime endDate;

    /**
     * Default constructor for JPA.
     */
    public Event(){};

    /**
     * Constructs an Event with the specified owner, name, start date, and end date.
     * @param owner the user who owns the event
     * @param name the name of the event
     * @param sd the start date and time
     * @param ed the end date and time
     */
    public Event(User owner, String name, ZonedDateTime sd, ZonedDateTime ed){
        this.owner = owner;
        this.name = name;
        this.startDate = sd;
        this.endDate = ed;
    }

    /**
     * Gets the unique identifier for the event.
     * @return the event ID
     */
    public Integer getId() {return this.id;}

    /**
     * Gets the user who owns the event.
     * @return the owner of the event
     */
    public User getOwner() {return this.owner;}

    /**
     * Gets the name of the event.
     * @return the event name
     */
    public String getName() {return this.name;}

    /**
     * Gets the start date and time of the event.
     * @return the start date and time
     */
    public ZonedDateTime getStartDate() {return this.startDate;}

    /**
     * Gets the end date and time of the event.
     * @return the end date and time
     */
    public ZonedDateTime getEndDate() {return this.endDate;}

    /**
     * Sets the unique identifier for the event.
     * @param id the event ID
     */
    public void setId(Integer id) {this.id=id;}

    /**
     * Sets the user who owns the event.
     * @param o the owner of the event
     */
    public void setOwner(User o) {this.owner=o;}

    /**
     * Sets the name of the event.
     * @param n the event name
     */
    public void setName(String n) {this.name=n;}

    /**
     * Sets the start date and time of the event.
     * @param sd the start date and time
     */
    public void setStartDate(ZonedDateTime sd) {this.startDate=sd;}

    /**
     * Sets the end date and time of the event.
     * @param ed the end date and time
     */
    public void setEndDate(ZonedDateTime ed) {this.endDate=ed;}
}
