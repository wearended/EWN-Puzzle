public class AIPlayer extends Player{
    // ============================================================
    // TODO: Implement chooseMove()
    // ------------------------------------------------------------
    // This method defines how the AI selects its next move.
    //
    // You are encouraged to implement any suitable AI or algorithmic
    // approach to decide the move, including rule-based strategy,
    // dynamic programming, greedy algorithm, and search-based algorithm
    //
    // Hardcoded solution (manually entering the correct moves)
    // is strictly not allowed.
    //
    // You may decide on the return type, parameters, and logic.
    // ============================================================

    // You may also add any other helper functions, variables,
    // and constructors needed for your implementation.

    static int calculateMoveDistance(int P, int Q){
        int pX = GameState.getXPosition(P), pY = GameState.getYPosition(P);
        int qX = GameState.getXPosition(Q), qY = GameState.getYPosition(Q);

        return Math.max(Math.abs(pX - qX), Math.abs(pY - qY));
    }
    
    static double calculateDistance(int P, int Q){
        int pX = GameState.getXPosition(P), pY = GameState.getYPosition(P);
        int qX = GameState.getXPosition(Q), qY = GameState.getXPosition(Q);

        return Math.sqrt(Math.pow(pX - qX, 2) + Math.pow(pY - qY, 2));
    }

    static boolean compareDistance(int TargetPosition, int P, int Q){
        int moveDistancePT = calculateMoveDistance(TargetPosition, P);
        int moveDistanceQT = calculateMoveDistance(TargetPosition, Q);

        if (moveDistancePT == moveDistanceQT){
            return calculateDistance(TargetPosition, P) < calculateDistance(TargetPosition, Q);
        }
        else return moveDistancePT < moveDistanceQT;
    }

    static int approachPosition(int[] PiecePossibleMoves, int TargetPosition, int AvoidPosition){
        int ClosestPositionToTarget = Integer.MAX_VALUE;

        for (int i = 0; i < PiecePossibleMoves.length; i++) {
            int Position = PiecePossibleMoves[i];
            if (Position == -1 || Position == AvoidPosition) continue;
            if (compareDistance(TargetPosition, Position, ClosestPositionToTarget)){
                ClosestPositionToTarget = Position;
            }
        }

        return ClosestPositionToTarget;
    }

    static int getClosestEdiblePiece(int[] PiecePositions, int Piece, int DoNotKill){
        int ClosestDistance = Integer.MAX_VALUE;
        int ClosestPiecePosition = -1;

        for (int i = 0; i < PiecePositions.length; i++) {
            if (i == DoNotKill || i == Piece || PiecePositions[i] == -1) continue;
            int DistanceToPiece = calculateMoveDistance(PiecePositions[i], PiecePositions[Piece]);
            if (DistanceToPiece < ClosestDistance){
                ClosestDistance = DistanceToPiece;
                ClosestPiecePosition = PiecePositions[i];
            }
        }

        return ClosestPiecePosition;
    }

    @Override
    public int choosePiece(boolean[] MovablePieces, GameLoader Game){
        int chosenPiece = -1;

        int TargetPiece = Game.TargetPiece;
        if (MovablePieces[TargetPiece]) chosenPiece = TargetPiece;
        else {
            for (int Piece = 0; Piece < MovablePieces.length; Piece++) {
                if (MovablePieces[Piece]) chosenPiece = Piece;
            }
        }

        return chosenPiece;
    }

    @Override
    public int chooseMove(int[] PiecePossibleMoves, int ChosenPiece, GameLoader Game){
        int chosenMove;
        
        int TargetPiecePosition = Game.PiecePositions[Game.TargetPiece];
        if (ChosenPiece == Game.TargetPiece){
            chosenMove = approachPosition(PiecePossibleMoves, 0, TargetPiecePosition);
        }
        else {
            int ClosestEdiblePiecePosition = getClosestEdiblePiece(Game.PiecePositions, ChosenPiece, Game.TargetPiece);
            System.out.println("Closest Edible Piece is at " + ClosestEdiblePiecePosition);
            System.out.println("Closest Position to Edible Piece is at " + approachPosition(PiecePossibleMoves, ClosestEdiblePiecePosition, TargetPiecePosition));
            if (ClosestEdiblePiecePosition == -1) chosenMove = approachPosition(PiecePossibleMoves, 0, TargetPiecePosition);
            else chosenMove = approachPosition(PiecePossibleMoves, ClosestEdiblePiecePosition, TargetPiecePosition);
        }
        
        System.out.println("AI moves Piece " + ChosenPiece + " to " + chosenMove);
        return chosenMove;
    }
}
