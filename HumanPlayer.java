import java.util.Scanner;

public class HumanPlayer extends Player{
    // ============================================================
    // TODO: Implement chooseMove()
    // ------------------------------------------------------------
    // This method prompts the human player to choose the next move
    //
    // You may decide on the return type, parameters, and logic.
    // ============================================================

    // You may also add any other helper functions, variables,
    // and constructors needed for your implementation.

    Scanner sc = new Scanner(System.in);
    String Name;
    public HumanPlayer(String Name){
        this.Name = Name;
    }

    @Override
    public int choosePiece(boolean[] MovablePieces){
        int chosenPiece = 0;

        System.out.println("\nAvailable Pieces: ");
        for (int i = 0; i < MovablePieces.length; i++){
            if (MovablePieces[i] == true){
                System.out.println(i + 1);
            }
        }

        while (chosenPiece == 0) {
            System.out.print("Choose Piece to Move: ");
            chosenPiece = sc.nextInt() - 1;

            if (chosenPiece < 0 || chosenPiece + 1 > MovablePieces.length){
               System.out.print("That piece is not available!: ");
                chosenPiece = 0;
            }
            else if (!MovablePieces[chosenPiece]) {
                System.out.print("That piece is not available!: ");
                chosenPiece = 0;
            }
        }

        return chosenPiece;
    }

    @Override
    public int chooseMove(int[] PiecePossibleMoves, int ChosenPiece){
        int chosenMove;
        
        System.out.println("\nPossible Moves Available: ");
        for (int i = 0; i < PiecePossibleMoves.length; i++){
            if (PiecePossibleMoves[i] != -1) System.out.print(PiecePossibleMoves[i] + " ");
        }

        while (true) {
            System.out.print("Choose Where to Move: ");
            chosenMove = sc.nextInt();
            if (Game.isMoveValid(chosenMove, PiecePossibleMoves)) break;
            else System.out.print("Choose A Valid Move!");
        }

        return chosenMove;
    }
}
