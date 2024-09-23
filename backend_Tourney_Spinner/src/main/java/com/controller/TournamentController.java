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

    // Endpoint to retrieve all teams in matches for a given tournament
    @GetMapping("/{tournamentId}/teams")
    public ResponseEntity<List<Team>> getAllTeamsInTournament(@PathVariable Long tournamentId) {
        List<Team> teams = tournamentService.getAllTeamsInMatches(tournamentId);
        return ResponseEntity.ok(teams);
    }

    // Define a new class to represent the request body
    public static class TournamentRequest {
        private String name;

        // Getter and setter
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @PostMapping
    public ResponseEntity<Tournament> createTournament(@RequestBody TournamentRequest tournamentRequest) {
        // Use the name property from the request body
        Tournament createdTournament = tournamentService.createTournament(tournamentRequest.getName());
        return ResponseEntity.status(201).body(createdTournament);
    }

    // Endpoint to create a team from player names
    @PostMapping("/createTeam")
    public ResponseEntity<Team> createTeam(@RequestBody PlayerPairRequest playerPairRequest) {
        // Pass teamName as an additional parameter
        Team createdTeam = tournamentService.createTeam(playerPairRequest.getPlayer1(), playerPairRequest.getPlayer2(), playerPairRequest.getTeamName());
        return ResponseEntity.status(201).body(createdTeam);
    }

    // Define a new class to represent the request body for creating a team
    public static class PlayerPairRequest {
        private String player1;
        private String player2;
        private String teamName;

        // Getters and setters
        public String getPlayer1() {
            return player1;
        }

        public void setPlayer1(String player1) {
            this.player1 = player1;
        }

        public String getPlayer2() {
            return player2;
        }

        public void setPlayer2(String player2) {
            this.player2 = player2;
        }

        // Getter and setter for teamName
        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }
    }

    // New endpoint to retrieve all teams from the database
    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = tournamentService.getAllTeams();  // Use the service method to get all teams
        return ResponseEntity.ok(teams);  // Return the list of teams
    }

    // Endpoint to delete a team by its id
    @DeleteMapping("/team/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
        tournamentService.deleteTeam(teamId);
        return ResponseEntity.noContent().build();  // Return 204 No Content on successful delete
    }
    
}
