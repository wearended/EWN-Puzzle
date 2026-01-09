
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GameLoader {
    int TargetPiece;
    int[] PiecePositions;
    int[] DiceSequence;

    public GameLoader(int Level){
        File LevelFile = new File("TestCases\\level" + Level + ".txt");
        try (Scanner LvlScanner = new Scanner(LevelFile)) {
            this.TargetPiece = LvlScanner.nextInt() - 1;
            this.PiecePositions = new int[6];
            this.DiceSequence = new int[30];
            
            for (int i = 0; i < PiecePositions.length; i++){
                PiecePositions[i] = LvlScanner.nextInt();
            }
            for (int i = 0; i < DiceSequence.length; i++){
                DiceSequence[i] = LvlScanner.nextInt();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error Opening Level: " + e.getMessage());
        }
    }

    public static boolean CanLoadLevel(int Level){
        File LevelFile = new File("TestCases\\level" + Level + ".txt");
        return LevelFile.exists();
    }

    public void printGameDetails(PrintWriter LevelWriter, String PlayerName){
        System.out.println("Player Name: " + PlayerName);
        LevelWriter.println(PlayerName);
        
        for (int i = 0; i < DiceSequence.length; i++){
            System.out.print(DiceSequence[i] + " ");
            LevelWriter.print(DiceSequence[i] + " ");
        }

        LevelWriter.print("\n" + (TargetPiece + 1));
        System.out.println("\nTargetPiece: " + (TargetPiece + 1));
    }
}
