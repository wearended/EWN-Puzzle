import java.io.PrintWriter;


public abstract class Player {
    // ============================================================
    // TODO: Implement printMove()
    // ------------------------------------------------------------
    // This method print the chosen move of the player into the
    // "moves.txt" file
    //
    // You may decide on the return type, parameters, and internal logic.
    // ============================================================

    // ============================================================
    // TODO: Implement abstract function - chooseMove()
    // ------------------------------------------------------------
    // This is an abstract method that defines how the player selects
    // a move. You should implement the logic in the subclasses.
    //
    // You may decide on the return type, and parameters.
    // ============================================================

    // You may also add any other helper functions, variables,
    // and constructors needed for your implementation.

    static void printMove(PrintWriter writer, int[] PiecePositions){
        System.out.println("");
        writer.println("");

        for (int y = 0; y < 10; y++){
            for (int x = 0; x < 10; x++){
                int PieceOverHere = -1;
                int Position = y * 10 + x;

                for (int i = 0; i < PiecePositions.length; i++){
                    if (PiecePositions[i] == Position){
                        PieceOverHere = i;
                    }
                }

                if (PieceOverHere != -1){
                    System.out.printf("%2s%d", "_", ++PieceOverHere);
                }
                else {
                    System.out.printf("%3d", Position);
                }
            }
            System.out.print("\n");
        }

        for (int i = 0; i < PiecePositions.length; i++){
            writer.print(PiecePositions[i] + " ");
        }
    }

    abstract int chooseMove(int[] PossibleMoves, int ChosenPiece, GameLoader Game);
    abstract int choosePiece(boolean[] MovablePieces, GameLoader Game);
}
