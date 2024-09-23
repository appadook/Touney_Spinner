import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api'; // Base URL for Spring Boot API

// Axios instance with base config
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Function to create a new tournament
export const createTournament = async (name: string) => {
  try {
    const response = await apiClient.post('/tournaments', { name });
    return response.data; // Return the response data
  } catch (error) {
    console.error('Error creating tournament:', error);
    throw error;
  }
};

// Function to fetch all tournaments
export const getAllTournaments = async () => {
  try {
    const response = await apiClient.get('/tournaments');
    return response.data;
  } catch (error) {
    console.error('Error fetching tournaments:', error);
    throw error;
  }
};

// Function to create a new team
export const createTeam = async (player1: string, player2: string, teamName: string) => {
  try {
    const response = await apiClient.post('/tournaments/createTeam', { player1, player2, teamName });
    return response.data; // Return the response data
  } catch (error) {
    console.error('Error creating team:', error);
    throw error;
  }
};

// Function to fetch all teams in a tournament
export const getTeamsInTournament = async (tournamentId: string) => {
  try {
    const response = await apiClient.get(`/tournaments/${tournamentId}/teams`);
    return response.data; // Return the response data
  } catch (error) {
    console.error('Error fetching teams:', error);
    throw error;
  }
};

// Function to fetch all teams from the database
export const getAllTeams = async () => {
    try {
      const response = await apiClient.get('/tournaments/teams'); // Endpoint to get all teams
      return response.data; // Return the response data
    } catch (error) {
      console.error('Error fetching all teams:', error);
      throw error;
    }
  };

  // Function to delete a team by its ID
export const deleteTeam = async (teamId: number) => {
    try {
      await apiClient.delete(`/tournaments/team/${teamId}`);
      console.log(`Team with id ${teamId} deleted successfully.`);
    } catch (error) {
      console.error(`Error deleting team with id ${teamId}:`, error);
      throw error;
    }
  };
