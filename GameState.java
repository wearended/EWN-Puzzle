import java.io.*;
import java.util.*;

public class GameState {

    int targetPiece;
    int[] piecePositions;
    int[] diceSequence;

    public GameState(GameLoader loader) {
        this.targetPiece = loader.targetPiece;
        this.piecePositions = loader.piecePositions;
        this.diceSequence = loader.diceSequence;
    }

    public void startGame(Player player) {
        try {
            System.out.println("\n=== GAME SETUP ===");
            System.out.println("Target piece to win: Piece #" + targetPiece);
            System.out.println("You have 30 moves maximum");

            PrintWriter outputFile = new PrintWriter("moves.txt");

            outputFile.println("=== SIMPLE EWN GAME ===");
            outputFile.println("Target: Piece " + targetPiece);
            outputFile.println("Starting positions:");
            for (int i = 1; i <= 6; i++) {
                if (piecePositions[i] != -1) {
                    outputFile.println("Piece " + i + " at square " + piecePositions[i]);
                }
            }
            outputFile.println("=".repeat(30));

            boolean won = false;

            for (int moveNumber = 1; moveNumber <= 30; moveNumber++) {
                int dice = diceSequence[moveNumber - 1];
                System.out.println("\n--- Move #" + moveNumber + " ---");
                System.out.println("Dice roll: " + dice);

                List<Integer> movablePieces =
                        GameRules.findMovablePieces(dice, piecePositions);

                if (movablePieces.isEmpty()) break;

                List<Move> allMoves =
                        GameRules.getAllPossibleMoves(movablePieces, piecePositions);

                Move chosenMove = player.chooseMove(allMoves);

                outputFile.println(
                        "Move " + moveNumber + ": Piece " +
                        chosenMove.pieceNumber + " from " +
                        piecePositions[chosenMove.pieceNumber] + " to " +
                        chosenMove.destination
                );

                GameRules.applyMove(chosenMove, piecePositions);

                if (piecePositions[targetPiece] == 0) {
                    outputFile.println("RESULT: WON in " + moveNumber + " moves");
                    won = true;
                    break;
                }
            }

            if (!won) {
                outputFile.println("RESULT: LOST");
            }

            outputFile.close();
        } catch (IOException e) {
            System.out.println("Error writing moves file");
        }
    }
}


