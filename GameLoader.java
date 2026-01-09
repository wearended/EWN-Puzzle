import java.io.PrintWriter;
import java.util.Scanner;

public class GameLoader {
    // ============================================================
    // TODO: Implement constructor
    // ------------------------------------------------------------
    // This method read data from the given filename and stores
    // them in appropriate variables
    //
    // You may decide on the return type, parameters, and internal logic.
    // ============================================================

    // ============================================================
    // TODO: Implement printGameDetails()
    // ------------------------------------------------------------
    // This method print the game setup details into "moves.txt"
    //
    // You may decide on the return type, parameters, and internal logic.
    // ============================================================

    // You may also add any other helper functions, variables,
    // and constructors needed for your implementation.

    int TargetPiece;
    int[] PiecePositions;
    int[] DiceSequence;
    int DiceRolls;
    int CurrentDiceRoll;

    public final void printGameDetails(PrintWriter writer, String PlayerName){
        System.out.println(PlayerName);
        writer.println(PlayerName);
        
        for (int i = 0; i < DiceSequence.length; i++){
            System.out.print(DiceSequence[i] + " ");
            writer.print(DiceSequence[i] + " ");
        }

        writer.print("\n" + (TargetPiece + 1));
        System.out.println("\nTargetPiece: " + (TargetPiece + 1));
    }

    public final void RollDice(){
        DiceRolls++;
        CurrentDiceRoll = DiceSequence[DiceRolls];
    }

    GameLoader(Scanner LvlScanner) {
        this.TargetPiece = LvlScanner.nextInt() - 1;
        this.PiecePositions = new int[6];
        this.DiceSequence = new int[30];
        this.CurrentDiceRoll = DiceSequence[0];
        this.DiceRolls = -1;

        for (int i = 0; i < PiecePositions.length; i++){
            PiecePositions[i] = LvlScanner.nextInt();
        }
        for (int i = 0; i < DiceSequence.length; i++){
            DiceSequence[i] = LvlScanner.nextInt();
        }
    }
}
