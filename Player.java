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

    String Name;
    GameState Game;

    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    static void printMove(PrintWriter writer, int[] PiecePositions, int TargetPiece){
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

                boolean IsIllegal = false;
                for (int i = 0; i < GameState.IllegalPositions.length; i++) {
                    if (GameState.IllegalPositions[i] == Position) {
                        IsIllegal = true;
                        break;
                    }
                }

                if (PieceOverHere != -1){
                    String ANSI = ANSI_BLUE;
                    if (PieceOverHere == TargetPiece) ANSI = ANSI_PURPLE;
                    System.out.printf(ANSI + "%2s%d", "_", ++PieceOverHere);
                }
                else {
                    String ANSI = ANSI_RESET;
                    if (Position == 0) {
                        ANSI = ANSI_GREEN;
                    }
                    else if (IsIllegal) {
                        ANSI = ANSI_RED;
                    }
                    System.out.printf(ANSI + "%3d", Position);
                }
            }
            System.out.print("\n");
        }

        for (int i = 0; i < PiecePositions.length; i++){
            writer.print(PiecePositions[i] + " ");
        }
    }

    abstract String getName();
    abstract int chooseMove(int[] PossibleMoves, int ChosenPiece);
    abstract int choosePiece(boolean[] MovablePieces);
}
