package com.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
public class Bracket {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roundName;   // E.g., "Quarter-final", "Semi-final"
    private boolean isWinnersBracket;  // To distinguish between winner's and loser's brackets

    @OneToMany(cascade = CascadeType.ALL)
    private List<Match> matches;   // Matches in this round

    public Bracket() {}

    public Bracket(String roundName, boolean isWinnersBracket, List<Match> matches) {
        this.roundName = roundName;
        this.isWinnersBracket = isWinnersBracket;
        this.matches = matches;
    }

    // Getters and Setters

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

    public void setWinnersBracket(boolean isWinnersBracket) {
        this.isWinnersBracket = isWinnersBracket;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }


}
