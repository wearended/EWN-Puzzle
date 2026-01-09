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

        Player NewPlayer;
        while (Choice == -1) {
            System.out.print("Select Game Mode: ");
            Choice = input.nextInt();

            switch(Choice) {
                case 1 ->  {
                    System.out.print("Enter Player Name: ");
                    String Name = input.next();
                    NewPlayer = new HumanPlayer(Name);
                    break;
                }
                case 2 ->  {
                    NewPlayer = new RandomPlayer();
                    break;
                }
                case 3 ->  {
                    NewPlayer = new AIPlayer();
                    break;
                }
                default -> {
                    System.out.println("Invalid Game Mode!");
                    Choice = -1; // Loop goes back to beginning if Choice = -1
                }
            }
        }
        
        // Ask for level file
        System.out.print("\nEnter Level: ");
        int Level = input.nextInt();
        
        // Start the game
        GameLoader Game = new GameLoader("TestCases\\level" + Level + ".txt");
        GameState NewGameState = new GameState(Game);
        NewGameState.startGame(NewPlayer);
        
        input.close();
    }
}

