package com.service;


import com.model.*;
import com.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections; // Add this import statement
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private TeamRepository teamRepository; // Inject Team repository

    @Autowired
    private BracketRepository bracketRepository; // Inject Bracket repository

    @Autowired
    private MatchRepository matchRepository; // Inject Match repository


    // Method to retrieve all teams from the database
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    // Method to delete a team by its id
    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }

    // Method to fetch all matches
    public List<Match> getAllMatches() {
        return matchRepository.findAll(); // Use JPA's built-in findAll method
    }

    // Method to generate the first round bracket and save to the database
    public List<Match> generateFirstRoundBracket(List<Team> teams, Long tournamentId) {
    Collections.shuffle(teams); // Randomly shuffle the teams
    List<Match> matches = new ArrayList<>(); // Single list for both bye and regular matches
    
    // Fetch the tournament using findById which returns an Optional<Tournament>
    Optional<Tournament> tournamentOptional = tournamentRepository.findById(tournamentId);

    // Check if the tournament exists
    if (!tournamentOptional.isPresent()) {
        throw new RuntimeException("Tournament not found with ID: " + tournamentId); // Handle not found
    }

    // Retrieve the Tournament object from the Optional
    Tournament tournament = tournamentOptional.get();

    // Create a new bracket for the first round
    Bracket firstRoundBracket = new Bracket("First Round", true); 
    firstRoundBracket.setTournament(tournament); // Associate the bracket with the tournament

    // Persist the bracket first
    firstRoundBracket = bracketRepository.save(firstRoundBracket);

    // Find the next power of 2 greater than or equal to the number of teams
    int n = teams.size();
    int nextPowerOfTwo = 1;
    while (nextPowerOfTwo < n) {
        nextPowerOfTwo *= 2;
    }

    // Calculate the number of byes
    int byes = nextPowerOfTwo - n;

    // Create both bye and regular matches in a single list
    for (int i = 0; i < n; i += 2) {
        if (i < byes) {
            // Create a bye match with only one team
            Team byeTeam = teams.get(i);
            Match byeMatch = new Match(byeTeam, true); // Use the constructor for a bye match
            byeMatch.setRoundName(firstRoundBracket.getRoundName()); // Set round name
            byeMatch.setBracket(firstRoundBracket); // Associate match with the bracket
            matches.add(byeMatch); // Add the bye match to the list

            // Persist the bye match
            matchRepository.save(byeMatch);
            System.out.println("Bye: " + byeTeam.getTeamName() + " automatically advances to the next round.");
        } else if (i + 1 < n) {
            // Create a regular match between two teams
            Team team1 = teams.get(i);
            Team team2 = teams.get(i + 1);
            Match match = new Match(team1, team2); // Use the constructor for a regular match
            match.setRoundName(firstRoundBracket.getRoundName()); // Set round name
            match.setBracket(firstRoundBracket); // Associate match with the bracket
            matches.add(match); // Add the match to the list

            // Persist the match
            matchRepository.save(match);
            System.out.println("Match: " + match.getTeam1().getTeamName() + " vs " + match.getTeam2().getTeamName());
        }
    }

    // Automatically generate subsequent rounds
    generateSubsequentRounds(matches.size(), tournament);

    return matches; // Return the list of matches (both bye and regular)
    } 



    // Recursive method to generate subsequent rounds
    private void generateSubsequentRounds(int numTeams, Tournament tournament) {
        int currentTeams = numTeams ;  // Teams halve after each round
        int roundNumber = 2; // Start with second round

        while (currentTeams >= 1) {
            String roundName = generateRoundName(currentTeams); // Generate round name dynamically
            Bracket nextRoundBracket = new Bracket(roundName, false);
            nextRoundBracket.setTournament(tournament);
            nextRoundBracket = bracketRepository.save(nextRoundBracket);

            // Generate empty matches for the round (teams will be filled when winners advance)
            for (int i = 0; i < currentTeams / 2; i++) {
                Match match = new Match(); // Empty match, winner of previous round will fill it later
                match.setRoundName(nextRoundBracket.getRoundName());
                match.setBracket(nextRoundBracket);
                matchRepository.save(match);
            }

            // Move to the next round
            currentTeams /= 2;
            roundNumber++;
        }
    }

    // Helper method to generate round names based on remaining teams
    private String generateRoundName(int teams) {
        if (teams == 2) {
            return "Final";
        } else if (teams == 4) {
            return "Semi-Final";
        } else if (teams == 8) {
            return "Quarter-Final";
        } else {
            return "Round of " + teams;
        }
    }


    // Method to delete all brackets, teams, and matches
    public void clearTournament() {
        // First delete all matches to prevent foreign key constraint violations
        matchRepository.deleteAll();
        System.out.println("All matches have been deleted.");

        // Then delete all brackets
        bracketRepository.deleteAll();
        System.out.println("All brackets have been deleted.");

        
    }


    // Retrieve all tournaments
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    // Create a new tournament using only the name
    public Tournament createTournament(String name) {
        Tournament tournament = new Tournament(name);
        return tournamentRepository.save(tournament);
    }

    // Method to create a team from player names
    public Team createTeam(String player1, String player2, String teamName) {
        Team team = new Team(player1, player2, teamName); // Assuming Team has a constructor that takes two player names
        return teamRepository.save(team); // Save the team to the database
    }

    // Method to get all teams playing in matches for a given tournament
    public List<Team> getAllTeamsInMatches(Long tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
            .orElseThrow(() -> new RuntimeException("Tournament not found")); // Fetch tournament

        List<Bracket> brackets = bracketRepository.findByTournament(tournament); // Fetch brackets for the tournament
        List<Team> teamsInMatches = new ArrayList<>(); // List to hold teams

        for (Bracket bracket : brackets) {
            List<Match> matches = matchRepository.findByBracket(bracket); // Fetch matches for each bracket
            for (Match match : matches) {
                if (match.getTeam1() != null) {
                    teamsInMatches.add(match.getTeam1()); // Add team1
                }
                if (match.getTeam2() != null) {
                    teamsInMatches.add(match.getTeam2()); // Add team2
                }
            }
        }

        return teamsInMatches; // Return the list of teams
    }

}

