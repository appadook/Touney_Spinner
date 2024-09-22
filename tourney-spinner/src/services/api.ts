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
