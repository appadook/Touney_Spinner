package com.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "tournaments") // Specify the table name
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Ensure the name is not null
    private String name;

    @Column(nullable = false) // Ensure isComplete is not null
    private boolean isComplete;

    // Add getter for id
    public Long getId() {
        return id;
    }

    // Add setter for id (optional, usually not needed)
    public void setId(Long id) {
        this.id = id;
    }

    // Add getter for name
    public String getName() {
        return name;
    }

    // Add getter for isComplete
    public boolean isComplete() {
        return isComplete;
    }

    // Add setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Add setter for isComplete
    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    // Default constructor
    public Tournament() {
    }

    // Constructor with name
    public Tournament(String name) {
        this.name = name;
        this.isComplete = false;
    }
}


