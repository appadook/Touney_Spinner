package com.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "matches")
public class Match {

    @Id  // Only keep this annotation from jakarta.persistence
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)  // Many matches can involve the same team
    @JoinColumn(name = "team1_id")  // Foreign key column in matches table for team1
    private Team team1;

    @ManyToOne(fetch = FetchType.LAZY)  // Many matches can involve the same team
    @JoinColumn(name = "team2_id")  // Foreign key column in matches table for team2
    private Team team2;

    private boolean isByeMatch;

    @ManyToOne(fetch = FetchType.LAZY)  // Many matches can reference one bracket
    @JoinColumn(name = "bracket_id", nullable = false)
    private Bracket bracket;  // Renamed from bracket_id

    @ManyToOne(fetch = FetchType.LAZY)  // Many matches can involve the same team
    @JoinColumn(name = "team_winner_id") 
    private Team winner;

    private String roundName; // Renamed from round_name

    public Match(Team team1, boolean bye) {
        this.team1 = team1;
        this.isByeMatch = bye;
    }

    public Match(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

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

    public Bracket getBracket() {
        return bracket;
    }

    public void setBracket(Bracket bracket) {
        this.bracket = bracket;
    }

    public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

}
