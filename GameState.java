import java.io.*;

public class GameState {

    int TargetPiece;
    int[] PiecePositions;
    int[] DiceSequence;
    int DiceRolls;
    PrintWriter LevelWriter;

    static int[] IllegalPositions = {22};

    public boolean[] getMovablePieces(int DiceRoll){
        boolean[] movablePieces = new boolean[PiecePositions.length];
        int supposedPiece = DiceRoll - 1;

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

    public GameState(GameLoader Loader) {
        this.TargetPiece = Loader.TargetPiece;
        this.PiecePositions = Loader.PiecePositions;
        this.DiceSequence = Loader.DiceSequence;
        this.LevelWriter = Loader.LevelWriter;
    }

    public int isWinning(){
        int TargetPiecePosition = PiecePositions[TargetPiece];

        // 1 - WON
        // 0 - GAME STILL ONGOING
        // -1 - LOST

        if (TargetPiecePosition == 0) return 1;
        else if (TargetPiecePosition == -1) return -1;
        else if (DiceRolls >= 30) return -1;
        else return 0;
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

                    for (int j = 0; j < IllegalPositions.length; j++) {
                        if (IllegalPositions[j] == YOffset * 10 + XOffset){
                            PossibleMoves[Piece][i] = -1;
                        }
                    }
                }
            }
        }

        return PossibleMoves;
    }

    public boolean isMoveValid(int chosenMove, int[] PiecePossibleMoves){
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

    public void applyMove(int ChosenPiece, int ChosenMove){

    }

    public void startGame(Player NewPlayer) {
        System.out.println("\n=== GAME SETUP ===");
        System.out.println("Target piece to win: Piece #" + TargetPiece);
        System.out.println("You have 30 moves maximum");
        
        NewPlayer.JoinGame(this);

        int MoveNumber = 1;
        while (true) { 
            int DiceRoll = DiceSequence[MoveNumber - 1];
            System.out.println("\n--- Move #" + MoveNumber + " ---");
            System.out.println("Dice roll: " + DiceRoll);

            if (isWinning() != 0) break;
            
            boolean[] MovablePieces = getMovablePieces(DiceRoll);
            int[][] AllMoves = generatePossibleMoves(PiecePositions, DiceRoll);
            
            Integer ChosenPiece = NewPlayer.choosePiece(MovablePieces);
            Integer ChosenMove = NewPlayer.chooseMove(AllMoves[ChosenPiece], ChosenPiece);

            applyMove(ChosenPiece, ChosenMove);

            ++MoveNumber;
        }

        

        LevelWriter.close();
    }
}


