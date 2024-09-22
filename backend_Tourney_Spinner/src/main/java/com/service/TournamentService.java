package com.service;


import com.repository.TournamentRepository;
import com.model.*;
import com.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentService {
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final BracketRepository bracketRepository;

    public TournamentService(TournamentRepository tournamentRepository,
                             TeamRepository teamRepository,
                             MatchRepository matchRepository,
                             BracketRepository bracketRepository) {
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
        this.bracketRepository = bracketRepository;
    }

    // Create a new tournament
    public Tournament createTournament(String name, List<Team> teams) {
        Tournament tournament = new Tournament(name);
        return tournamentRepository.save(tournament);
    }

    // Retrieve all tournaments
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    // Add a new round to the tournament (for both winners' and losers' brackets)
    public Bracket createBracket(Tournament tournament, List<Match> matches, boolean isWinnersBracket) {
        Bracket bracket = new Bracket(isWinnersBracket ? "Winners Round" : "Losers Round", isWinnersBracket);
        return bracketRepository.save(bracket);
    }

    // Example of retrieving all teams
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
}

