package com.controller;

import com.model.*;
import com.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    // Endpoint to generate the first round bracket
    @PostMapping("/{tournamentId}/generateFirstRound")
    public ResponseEntity<List<Match>> generateFirstRoundBracket(@PathVariable Long tournamentId, @RequestBody List<Team> teams) {
        // Fetch the list of teams for the given tournament (this would be implemented in your service)
        List<Team> fetchedTeams = teams; 

        // Generate the first round bracket
        List<Match> matches = tournamentService.generateFirstRoundBracket(fetchedTeams);

        // Return the generated matches
        return ResponseEntity.ok(matches);
    }

    // Retrieve all tournaments
    @GetMapping
    public List<Tournament> getAllTournaments() {
        return tournamentService.getAllTournaments();
    }

    // Endpoint to create a new tournament with only the name
    @PostMapping
    public ResponseEntity<Tournament> createTournament(@RequestBody String name) {
        Tournament createdTournament = tournamentService.createTournament(name);
        return ResponseEntity.status(201).body(createdTournament);
    }
    
}
