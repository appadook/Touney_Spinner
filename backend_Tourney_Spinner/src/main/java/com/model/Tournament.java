package com.model;
import jakarta.persistence.*;

import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
public class Tournament {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean isComplete;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Team> teams;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Bracket> winnersBrackets;  // List of winner's bracket rounds

    @OneToMany(cascade = CascadeType.ALL)
    private List<Bracket> losersBrackets;   // List of loser's bracket rounds

    public Tournament() {
    }

    public Tournament(String name, List<Team> teams) {
        this.name = name;
        this.teams = teams;
        this.isComplete = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Bracket> getWinnersBrackets() {
        return winnersBrackets;
    }

    public void setWinnersBrackets(List<Bracket> winnersBrackets) {
        this.winnersBrackets = winnersBrackets;
    }

    public List<Bracket> getLosersBrackets() {
        return losersBrackets;
    }

    public void setLosersBrackets(List<Bracket> losersBrackets) {
        this.losersBrackets = losersBrackets;
    }


}


