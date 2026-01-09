public class AIPlayer extends Player{
    public AIPlayer(String PlayerName, GameState PlayerGame){
        this.Name = PlayerName;
        this.Game = PlayerGame;
    }

    @Override
    public String getName() {return Name;}

    static int calculateMoveDistance(int P, int Q){
        if (P == -1 || Q == -1) return 99;

        int pX = GameState.getXPosition(P), pY = GameState.getYPosition(P);
        int qX = GameState.getXPosition(Q), qY = GameState.getYPosition(Q);

        return Math.max(Math.abs(pX - qX), Math.abs(pY - qY));
    }
    
    public int findClosestEdiblePiece(int Piece, int Position){
        int ClosestDistance = Integer.MAX_VALUE;
        int ClosestPiecePosition = -1;

        for (int i = 0; i < Game.PiecePositions.length; i++) {
            if (i == Game.TargetPiece || i == Piece || Game.PiecePositions[i] == -1) continue;
            int DistanceToPiece = calculateMoveDistance(Game.PiecePositions[i], Position);
            if (DistanceToPiece < ClosestDistance){
                ClosestDistance = DistanceToPiece;
                ClosestPiecePosition = Game.PiecePositions[i];
            }
        }

        return ClosestPiecePosition;
    }

    public int evaluateMove(int Piece, int CurrentPosition, int TargetPosition){
        if (TargetPosition == -1) return 0;

        int TargetToZero = calculateMoveDistance(TargetPosition, 0);
        int CurrentToZero = calculateMoveDistance(CurrentPosition, 0);

        int EdiblePiece1 = findClosestEdiblePiece(Piece, CurrentPosition);
        
        int EdiblePiece1ToTarget = calculateMoveDistance(EdiblePiece1, TargetPosition);
        int EdiblePiece1ToPiece = calculateMoveDistance(EdiblePiece1, CurrentPosition);

        if (Piece == Game.TargetPiece) {
            
            if (CurrentToZero > EdiblePiece1ToTarget * Game.countLivePieces() * 2) {
                System.out.println(CurrentToZero + " " + EdiblePiece1ToTarget * Game.countLivePieces());
                // Prioritize capturing other pieces
                if (EdiblePiece1ToTarget < EdiblePiece1ToPiece){
                    return 200;
                }
                return 150;
            }

            if (TargetToZero < CurrentToZero) {
                if (EdiblePiece1ToTarget == 0) return 150;
                if (TargetPosition == 33) return 99;
                return 100;
            } else if (TargetToZero == CurrentToZero) {
                if (EdiblePiece1ToTarget < EdiblePiece1ToPiece){
                    return 50;
                }
                else return 25;
            }
            else return 0;
        } else {
            if (Game.pieceAtPosition(TargetPosition) == Game.TargetPiece) return 0;

            int EdiblePiece2 = findClosestEdiblePiece(Piece, TargetPosition);
            int EdiblePiece2ToTarget = calculateMoveDistance(EdiblePiece2, TargetPosition);
            int EdiblePiece2ToZero = calculateMoveDistance(EdiblePiece2, 0);
            
            int HighestScore = 100 - EdiblePiece2ToTarget - 1;
            for (int OtherPiece = 0; OtherPiece < Game.PiecePositions.length; OtherPiece++) {
                if (OtherPiece == Piece || OtherPiece == Game.TargetPiece) continue;
                
                int Distance = calculateMoveDistance(TargetPosition, Game.PiecePositions[OtherPiece]);
                int Score = 100 - Distance - 1;
                if (Score > HighestScore){
                    HighestScore = Score;
                }
            }
            
            if (HighestScore == -1){ // That means other pieces are dead except the target piece
                int DistanceToZero = calculateMoveDistance(TargetPosition, 0);
                int TargetPieceToZero = calculateMoveDistance(Game.PiecePositions[Game.TargetPiece], 0);
                if (DistanceToZero <= TargetPieceToZero){
                    HighestScore = 100;
                }
            }

            if (TargetToZero <= EdiblePiece2ToZero){
                HighestScore += 1;
            }

            return HighestScore;
        }
    }

    public int evaluatePiece(int Piece){
        if (Piece == Game.TargetPiece) return 200; // Absolutely move the target piece!!!
        else {
            int Score1 = ((Math.abs(Game.TargetPiece - Piece) - 1) * 100 / 5); // The farther the piece number to the target piece, the higher
            
            int ClosestEdiblePosition = findClosestEdiblePiece(Piece, Game.PiecePositions[Piece]);
            int DistanceFromEdiblePiece = calculateMoveDistance(Game.PiecePositions[Piece], ClosestEdiblePosition) - 1;
            int Score2 = 100 - DistanceFromEdiblePiece; // The closer the piece to an edible piece, the higher.

            //System.out.println(Piece + " " + Score1 + " " + Score2);
            return (Score1 + Score2) / 2;
        }
    }

    @Override
    public int choosePiece(boolean[] MovablePieces){
        int ChosenPiece = -1;

        int HighestEvaluationScore = Integer.MIN_VALUE;
        for (int Piece = 0; Piece < MovablePieces.length; Piece++) {
            if (!MovablePieces[Piece]) continue;
            int Score = evaluatePiece(Piece);
            //System.out.println("Score of Piece #" + (Piece + 1) + ": " + Score);
            if (Score > HighestEvaluationScore) {
                HighestEvaluationScore = Score;
                ChosenPiece = Piece;
            }
        }

        System.out.println("AI chooses Piece #" + (ChosenPiece + 1));
        return ChosenPiece;
    }

    @Override
    public int chooseMove(int[] PiecePossibleMoves, int ChosenPiece){
        int ChosenMove = PiecePossibleMoves[0];
        
        int HighestEvaluationScore = Integer.MIN_VALUE;
        int PieceCurrentPosition = Game.PiecePositions[ChosenPiece];

        for (int i = 0; i < PiecePossibleMoves.length; i++) {
            int TargetPosition = PiecePossibleMoves[i];
            int Score = evaluateMove(ChosenPiece, PieceCurrentPosition, TargetPosition);
            System.out.println("Score of " + TargetPosition + ": " + Score);
            if (Score > HighestEvaluationScore) {
                HighestEvaluationScore = Score;
                ChosenMove = TargetPosition;
            }
        }
        
        System.out.println("AI moves Piece #" + (ChosenPiece + 1) + " to " + ChosenMove);
        return ChosenMove;
    }
}