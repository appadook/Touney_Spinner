import { useState } from "react"; 
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import './index.css';
import { createTournament } from './services/api'; // Import the API function

function App() {
  const [inputValue, setInputValue] = useState(''); // State to hold the tournament name
  const [loading, setLoading] = useState(false); // State to handle loading
  

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault(); 
    await submitForm();
  };

  const submitForm = async () => {
    setLoading(true);
    try {
      await createTournament(inputValue); // Call the API function to create a tournament
      alert(`Tournament "${inputValue}" created successfully!`);
      setInputValue(''); // Clear the input after successful submission
    } catch (error: unknown) {
      const errorMessage = error instanceof Error ? error.message : 'Unknown error';
      alert(`Failed to create tournament: ${errorMessage}`);
    } finally {
      setLoading(false); // Stop loading after the process finishes
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 dark:bg-gray-900">
      <div className="w-full max-w-md p-8 space-y-4 bg-blue dark:bg-gray-800 rounded-lg shadow-md">
        <h1 className="text-2xl font-bold text-center text-gray-900 dark:text-gray-100">
          New Tourney
        </h1>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="input" className="sr-only">
              Input
            </label>
            <Input
              id="input"
              type="text"
              placeholder="Enter your Tournament name"
              value={inputValue}
              onChange={(e) => setInputValue(e.target.value)}
              required
              autoComplete="off" // Disable autocomplete suggestions
            />
          </div>
          <Button 
            type="submit" 
            className="w-full"
            disabled={loading} // Disable the button while loading
          >
            {loading ? 'Creating...' : 'Create New Tournament'}
          </Button>
        </form>
      </div>
    </div>
  );
}

export default App;
