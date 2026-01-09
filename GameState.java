public class GameState {

    // ============================================================
    // TODO: Implement generatePossibleMoves()
    // ------------------------------------------------------------
    // This method should generate all possible moves based on:
    //  - The current piece positions
    //  - The current dice roll
    //
    // You may decide on the return type, parameters, and internal logic.
    // ============================================================

    // ============================================================
    // TODO: Implement isWinning()
    // ------------------------------------------------------------
    // This method should check whether the current piece positions
    // fulfill the winning condition
    //
    // You may decide on the return type, parameters, and internal logic.
    // ============================================================

    // You may also add any other helper functions, variables,
    // and constructors needed for your implementation.

    public static boolean[] getMovablePieces(int[] PiecePositions, int CurrentDiceRoll){
        boolean[] movablePieces = new boolean[PiecePositions.length];
        int supposedPiece = CurrentDiceRoll - 1;

        if (PiecePositions[supposedPiece] != -1) movablePieces[supposedPiece] = true;
        else {
            for (int i = supposedPiece + 1; i < PiecePositions.length; i++){
                if (PiecePositions[i] != -1){
                    movablePieces[i] = true;
                    break;
                }
            }
            for (int i = supposedPiece - 1; i >= 0; i--){
                if (PiecePositions[i] != -1){
                    movablePieces[i] = true;
                    break;
                }
            }
        }
        return movablePieces;
    }

    public static int getYPosition(int Position){
        return Position / 10;
    }

    public static int getXPosition(int Position){
        return Position % 10;
    }

    public static int[][] PossibleMoveOffsets = {
        { 0,  1},
        { 0, -1},
        { 1,  0},
        {-1,  0},
        { 1,  1},
        { 1, -1},
        {-1,  1},
        {-1, -1},
    };

    public static int[] DisabledPositions = {22};

    public static int[][] generatePossibleMoves(int[] PiecePositions, int CurrentDiceRoll){
        int[][] PossibleMoves = new int[6][8];

        for (int Piece = 0; Piece < PiecePositions.length; Piece++){
            int PositionOfPiece = PiecePositions[Piece];

            // Set All Piece's Possible Moves to -1 by Default
            for (int i = 0; i < PossibleMoves[Piece].length; i++) {
                PossibleMoves[Piece][i] = -1;
            }

            if (PositionOfPiece != -1) {
                int X = GameState.getXPosition(PositionOfPiece);
                int Y = GameState.getYPosition(PositionOfPiece);
                
                for (int i = 0; i < PossibleMoveOffsets.length; i++) {
                    int XOffset = X + PossibleMoveOffsets[i][0];
                    int YOffset = Y + PossibleMoveOffsets[i][1];
                    
                    if (XOffset >= 0 && XOffset <= 9 && YOffset >= 0 && YOffset <= 9){
                        PossibleMoves[Piece][i] = YOffset * 10 + XOffset;
                    }

                    for (int j = 0; j < DisabledPositions.length; j++) {
                        if (DisabledPositions[j] == YOffset * 10 + XOffset){
                            PossibleMoves[Piece][i] = -1;
                        }
                    }
                }
            }
        }

        return PossibleMoves;
    }

    public static int isWinning(GameLoader Game){
        int TargetPiecePosition = Game.PiecePositions[Game.TargetPiece];

        // 1 - WON
        // 0 - GAME STILL ONGOING
        // -1 - LOST

        if (TargetPiecePosition == 0) return 1;
        else if (TargetPiecePosition == -1) return -1;
        else if (Game.DiceRolls >= 29) return -1;
        else return 0;
    }

    public static boolean isMoveValid(int chosenMove, int[] PiecePossibleMoves){
        boolean isValidMove = false;

        if (chosenMove == -1) isValidMove = false;
        else {
            for (int i = 0; i < PiecePossibleMoves.length; i++){
                if (chosenMove == PiecePossibleMoves[i]) {
                    isValidMove = true;
                    break;
                }
            }
        }

        return isValidMove;
    }

    public static void playMove(int PieceToMove, int TargetPosition, GameLoader Game){
        for (int Piece = 0; Piece < Game.PiecePositions.length; Piece++){
            if (PieceToMove == Piece){
                Game.PiecePositions[Piece] = TargetPosition;
            }
            else if (TargetPosition == Game.PiecePositions[Piece]){
                Game.PiecePositions[Piece] = -1; // Bro Gets Eaten!!!
                System.out.println((PieceToMove + 1) + " has eaten " + (Piece + 1));
            }
        }
    }
}
