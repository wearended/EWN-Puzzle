import java.util.Scanner;

public class GameMain {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("=== SIMPLE EWN PUZZLE GAME ===");
        System.out.println("Welcome to the puzzle game!");
        
        // Ask for player type
        int Choice = -1;
        System.out.println("\nChoose your Game Mode:");
        System.out.println("1. I want to play myself (Human)");
        System.out.println("2. Let computer play randomly");
        System.out.println("3. Let computer AI play");

        String PlayerName = "Unnamed";
        while (Choice == -1) {
            System.out.print("Select Game Mode: ");
            Choice = input.nextInt();

            switch(Choice) {
                case 1 ->  {
                    System.out.print("Enter Player Name: ");
                    PlayerName = input.next();
                    break;
                }
                case 2 ->  {
                    PlayerName = "Random Player";
                    break;
                }
                case 3 ->  {
                    PlayerName = "AI Player";
                    break;
                }
                default -> {
                    System.out.println("Invalid Game Mode!");
                    Choice = -1; // Loop goes back to beginning if Choice = -1
                }
            }
        }
        
        while (true){
            // Ask for level file
            System.out.print("Enter Level: ");
            int Level = input.nextInt();
            
            // Check Whether Level is available
            if (GameLoader.CanLoadLevel(Level)){
                // Start the game
                GameLoader Game = new GameLoader(Level);
                GameState NewGameState = new GameState(Game);
                Player NewPlayer;
                switch(Choice) {
                    case 1 ->  {
                        NewPlayer = new HumanPlayer(PlayerName, NewGameState);
                        break;
                    }
                    case 2 ->  {
                        NewPlayer = new RandomPlayer(PlayerName, NewGameState);
                        break;
                    }
                    default -> {
                        NewPlayer = new AIPlayer(PlayerName, NewGameState);
                    }
                }
                NewGameState.startGame(NewPlayer);
                break;
            } else {
                System.out.println("Sorry, this level isn't available!");
            }
        }

        input.close();
    }
}

