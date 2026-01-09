import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GameMain {
    public static void main(String[] args){
        // ============================================================
        // TODO: Implement the main() function
        // ------------------------------------------------------------
        // This is the main entry point of the program.
        //
        // The function should perform the following tasks:
        //
        // 1. Prompt the user to choose a game mode:
        //      - Human Player
        //      - Random Player
        //      - AI Player
        //
        // 2. Create a player object based on the selected mode:
        //      - For Human Player => prompt for player name.
        //      - For Random Player => use default name "Random Player".
        //      - For AI Player => use default name "AI Player".
        //
        // 3. Prompt the user to select a level.
        //
        // 4. Call the chooseMove() function of the player
        //    to perform their move based on the current game state.
        //
        // 5. Display the result of the game
        //
        // ============================================================

        // You may also add any other helper functions, variables,
        // and constructors needed for your implementation.

        Scanner sc = new Scanner(System.in);
        int GameModeInput = 0;

        String GameModeName = "";
        String Name = "";
        Player NewPlayer = new HumanPlayer();

        // Game Modes
        // 1: Human Player
        // 2: Random Player
        // 3: AI Player

        System.out.println("1: Human Player\n2: Random Player\n3: AI Player");

        while (GameModeInput == 0) {
            System.out.print("Enter Game Mode: ");
            GameModeInput = sc.nextInt();

            switch(GameModeInput) {
                case 1 ->  {
                    NewPlayer = new HumanPlayer();
                    GameModeName = "Human Player";
                    System.out.print("Enter Player Name: ");
                    Name = sc.next();
                }
                case 2 ->  {
                    NewPlayer = new RandomPlayer();
                    GameModeName = "Random Player";
                    Name = "Random Player";
                }
                case 3 ->  {
                    NewPlayer = new AIPlayer();
                    GameModeName = "AI Player";
                    Name = "AI Player";
                }
                default -> {
                    System.out.println("Invalid Game Mode!");
                    GameModeInput = 0; // Loop goes back to beginning if GameModeInput == 0
                }
            }
        }
        
        int LevelInput = 0;
        while (LevelInput == 0) {
            System.out.print("Enter Level: ");
            LevelInput = sc.nextInt();
            File LevelFile = new File("TestCases\\level" + LevelInput + ".txt");
            
            try (Scanner LvlScanner = new Scanner(LevelFile)) {
                GameLoader Game = new GameLoader(LvlScanner);
                try (PrintWriter writer = new PrintWriter(new File("moves.txt"))) {

                    Game.printGameDetails(writer, Name);

                    int[] PiecePositions = Game.PiecePositions; 
                    int Moves = 0;

                    Player.printMove(writer, PiecePositions);
                    while (GameState.isWinning(Game) == 0){
                        Game.RollDice();
                        
                        boolean[] MovablePieces = GameState.getMovablePieces(PiecePositions, Game.CurrentDiceRoll);
                        int[][] PossibleMoves = GameState.generatePossibleMoves(PiecePositions, Game.CurrentDiceRoll);
                        int ChosenPiece = NewPlayer.choosePiece(MovablePieces, Game);
                        int ChosenMove = NewPlayer.chooseMove(PossibleMoves[ChosenPiece], ChosenPiece, Game);

                        GameState.playMove(ChosenPiece, ChosenMove, Game);
                        Player.printMove(writer, PiecePositions);
                        Moves++;
                    }
                    writer.close();

                    System.out.println("\nGame Finished!");
                    System.out.println("Game Won: " + (GameState.isWinning(Game) == 1));
                    System.out.println("Move Count: " + Moves);
                } catch (FileNotFoundException e) {
                    System.err.println("Error Opening moves.txt: " + e.getMessage());
                }
            } catch (FileNotFoundException e) {
                System.out.println("Sorry, level " + LevelInput + " isn't available. Please enter another level.");
                LevelInput = 0;
            }
        }
    }
}

