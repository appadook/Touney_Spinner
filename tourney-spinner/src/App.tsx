import { useState, useEffect } from "react"; 
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { PlusCircle, Trash2, Send } from "lucide-react";
import './index.css';
import { createTournament, createTeam, getTeamsInTournament, getAllTeams, deleteTeam } from './services/api'; // Import the API functions


interface PlayerPair {
  player1: string;
  player2: string;
  teamName: string
}

interface Team {
  id: number
  player1: string;
  player2: string;
  teamName: string
}

export default function App() {
  const [tournamentName, setTournamentName] = useState('');
  const [playerPairs, setPlayerPairs] = useState<PlayerPair[]>([{ player1: '', player2: '', teamName: ''}]);
  const [loading, setLoading] = useState(false);
  const [submittingPair, setSubmittingPair] = useState<number | null>(null);
  const [submittedPlayerPairs, setSubmittedPlayerPairs] = useState<Team[]>([]); // State to store submitted player pairs
  const [shouldFetchTeams, setShouldFetchTeams] = useState(false); 

  // Fetch submitted player pairs when the tournament is created
  useEffect(() => {
    const fetchPlayerPairs = async () => {
      console.log('Fetching player pairs...'); // Debug log
      try {
        const fetchedPlayerPairs = await getAllTeams(); // Fetch player pairs from API
        console.log('Data fetched:', fetchedPlayerPairs); // Debug log
        setSubmittedPlayerPairs(fetchedPlayerPairs); // Update state with fetched player pairs
      } catch (error: unknown) {
        const errorMessage = error instanceof Error ? error.message : 'Unknown error';
        alert(`Failed to fetch player pairs: ${errorMessage}`);
      }
    };

    fetchPlayerPairs(); // Fetch player pairs on component mount
  }, [shouldFetchTeams]); // Empty dependency array to run only on mount

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault(); 
    await submitForm();
  };

  const submitForm = async () => {
    setLoading(true);
    try {
      await createTournament(tournamentName);
      alert(`Tournament "${tournamentName}" created successfully with ${playerPairs.length} player pairs!`);
      setTournamentName('');
      setPlayerPairs([{ player1: '', player2: '', teamName:'' }]);
      setShouldFetchTeams(!shouldFetchTeams); 
    } catch (error: unknown) {
      const errorMessage = error instanceof Error ? error.message : 'Unknown error';
      alert(`Failed to create tournament: ${errorMessage}`);
    } finally {
      setLoading(false);
    }
  };

  const handlePlayerChange = (index: number, player: 'player1' | 'player2' | 'teamName', value: string) => {
    const newPlayerPairs = [...playerPairs];
    newPlayerPairs[index][player] = value;

    // Update teamName dynamically if it's empty
    if (player === 'player1' || player === 'player2') {
        const teamName = newPlayerPairs[index].player1 && newPlayerPairs[index].player2
            ? `${newPlayerPairs[index].player1} & ${newPlayerPairs[index].player2}`
            : '';
        newPlayerPairs[index].teamName = teamName;
    }

    setPlayerPairs(newPlayerPairs);
  };

  const addPlayerPair = () => {
    setPlayerPairs([...playerPairs, { player1: '', player2: '', teamName: '' }]);
  };

  const removePlayerPair = (index: number) => {
    if (index === 0 && playerPairs.length === 1) {
      // Don't remove the last pair
      return;
    }
    const newPlayerPairs = playerPairs.filter((_, i) => i !== index);
    setPlayerPairs(newPlayerPairs);
  };

  const handleSubmitPair = async (index: number) => {
    setSubmittingPair(index);
    try {
      const pair = playerPairs[index];
      await createTeam(pair.player1, pair.player2, pair.teamName);
      alert(`Team "${pair.player1} & ${pair.player2}" added to the database successfully!`);
      setShouldFetchTeams(!shouldFetchTeams);
      // Optionally, you can clear the inputs after successful submission
      // handlePlayerChange(index, 'player1', '');
      // handlePlayerChange(index, 'player2', '');
    } catch (error: unknown) {
      const errorMessage = error instanceof Error ? error.message : 'Unknown error';
      alert(`Failed to add team to database: ${errorMessage}`);
    } finally {
      setSubmittingPair(null);
    }
  };

  const handleDeleteTeam = async (teamId: number) => {
    try {
      await deleteTeam(teamId);
      alert(`Team deleted successfully!`);
      setShouldFetchTeams(!shouldFetchTeams); 
    } catch (error: unknown) {
      const errorMessage = error instanceof Error ? error.message : 'Unknown error';
      alert(`Failed to delete team: ${errorMessage}`);
    }
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-50 dark:bg-gray-900 p-4">
      <div className="w-full max-w-md p-8 mb-8 bg-white dark:bg-gray-800 rounded-lg shadow-md">
        <h1 className="text-2xl font-bold text-center text-gray-900 dark:text-gray-100 mb-4">
          New Tourney
        </h1>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="tournamentName" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              Tournament Name
            </label>
            <Input
              id="tournamentName"
              type="text"
              placeholder="Enter your Tournament name"
              value={tournamentName}
              onChange={(e) => setTournamentName(e.target.value)}
              required
              autoComplete="off"
            />
          </div>
          <Button 
            type="submit" 
            className="w-full"
            disabled={loading}
          >
            {loading ? 'Creating...' : 'Create New Tournament'}
          </Button>
        </form>
      </div>

      <div className="w-full max-w-2xl p-8 bg-white dark:bg-gray-800 rounded-lg shadow-md">
        <h2 className="text-xl font-bold text-center text-gray-900 dark:text-gray-100 mb-4">
          Player Pairs
        </h2>
        {playerPairs.map((pair, index) => (
          <div key={index} className="flex items-center space-x-2 mb-4">
            <div className="flex-grow flex space-x-2">
              <Input
                type="text"
                placeholder="Player 1"
                value={pair.player1}
                onChange={(e) => handlePlayerChange(index, 'player1', e.target.value)}
                required
              />
              <Input
                type="text"
                placeholder="Player 2"
                value={pair.player2}
                onChange={(e) => handlePlayerChange(index, 'player2', e.target.value)}
                required
              />
              <Input
                type="text"
                placeholder="Team Name"
                value={pair.teamName}
                onChange={(e) => handlePlayerChange(index, 'teamName', e.target.value)}
                required
              />
            </div>
            <Button 
              type="button" 
              variant="ghost" 
              size="icon"
              onClick={() => removePlayerPair(index)}
              disabled={index === 0 && playerPairs.length === 1}
              aria-label={`Remove player pair ${index + 1}`}
            >
              <Trash2 className="h-4 w-4" />
            </Button>
            <Button
              type="button"
              variant="outline"
              size="icon"
              onClick={() => handleSubmitPair(index)}
              disabled={submittingPair === index || !pair.player1 || !pair.player2 || !pair.teamName}
              aria-label={`Submit player pair ${index + 1}`}
            >
              <Send className="h-4 w-4" />
            </Button>
          </div>
        ))}
        <Button 
          type="button" 
          onClick={addPlayerPair}
          variant="outline"
          size="sm"
          className="w-full mt-2"
        >
          <PlusCircle className="h-4 w-4 mr-2" />
          Add Player Pair
        </Button>
      </div>

      <div className="w-full max-w-md p-8 bg-white dark:bg-gray-800 rounded-lg shadow-md">
        <h2 className="text-xl font-bold text-center text-gray-900 dark:text-gray-100 mb-4">
          Submitted Teams
        </h2>
        <ul className="space-y-2">
        {submittedPlayerPairs.map((pair, index) => {
      const defaultTeamName = `${pair.player1} & ${pair.player2}`;
      return (
        <li
          key={index}
          className="flex justify-between items-center p-3 bg-gray-100 dark:bg-gray-700 rounded"
        >
          <span className="text-gray-900 dark:text-gray-100">
            {pair.teamName !== defaultTeamName
              ? `${pair.teamName}: ${pair.player1} & ${pair.player2}`
              : `${pair.teamName}`}
          </span>
          <Button 
              type="button" 
              variant="ghost" 
              size="icon"
              onClick={() => handleDeleteTeam(pair.id)}
              aria-label={`Remove player pair ${index + 1}`}
            >
              <Trash2 className="h-4 w-4" />
            </Button>
        </li>
      );
    })}
  </ul>
      </div>
    </div>
  );
}