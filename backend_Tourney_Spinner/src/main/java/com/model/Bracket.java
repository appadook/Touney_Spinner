package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;



@Entity
@Table(name = "brackets")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bracket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roundName;   // E.g., "Quarter-final", "Semi-final"
    private boolean isWinnersBracket;  // To distinguish between winner's and loser's brackets

    @ManyToOne(fetch = FetchType.LAZY)  // Many brackets can reference one tournament
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    public Bracket() {}

    public Bracket(String roundName, boolean isWinnersBracket) {
        this.roundName = roundName;
        this.isWinnersBracket = isWinnersBracket;
     
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public boolean isWinnersBracket() {
        return isWinnersBracket;
    }

    public void setWinnersBracket(boolean winnersBracket) {
        isWinnersBracket = winnersBracket;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}
