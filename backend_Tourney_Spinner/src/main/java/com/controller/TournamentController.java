package com.controller;

import com.model.Tournament;
import com.model.Team;
import com.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    // Create a new tournament
    @PostMapping("/create")
    public Tournament createTournament(@RequestParam String name, @RequestBody List<Team> teams) {
        return tournamentService.createTournament(name, teams);
    }

    // Retrieve all tournaments
    @GetMapping
    public List<Tournament> getAllTournaments() {
        return tournamentService.getAllTournaments();
    }

    // Retrieve all teams
    @GetMapping("/teams")
    public List<Team> getAllTeams() {
        return tournamentService.getAllTeams();
    }
}
