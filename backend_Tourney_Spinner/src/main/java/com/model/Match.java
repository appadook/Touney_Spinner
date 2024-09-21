package com.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import org.springframework.data.annotation.Id;



@Entity
public class Match {


    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Team team1;

    @ManyToOne
    private Team team2;

    private boolean isByeMatch;

    public Match() {}

    public Match(Team team1, Team team2, boolean isByeMatch) {
        this.team1 = team1;
        this.team2 = team2;
        this.isByeMatch = isByeMatch;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public boolean isByeMatch() {
        return isByeMatch;
    }

    public void setByeMatch(boolean isByeMatch) {
        this.isByeMatch = isByeMatch;
    }



}
