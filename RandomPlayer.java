import java.util.Random;

public class RandomPlayer extends Player{
    // ============================================================
    // TODO: Implement chooseMove()
    // ------------------------------------------------------------
    // This method randomly choose the moves to solve the puzzle
    //
    // You may decide on the return type, parameters, and logic.
    // ============================================================

    // You may also add any other helper functions, variables,
    // and constructors needed for your implementation.

    Random rng = new Random();

    @Override
    public int choosePiece(boolean[] MovablePieces, GameLoader Game){
        int chosenPiece;

        while (true) { 
            chosenPiece = rng.nextInt(6);
            if (MovablePieces[chosenPiece]) break;
        }

        return chosenPiece;
    }

    @Override
    public int chooseMove(int[] PiecePossibleMoves, int ChosenPiece, GameLoader Game){
        int chosenMove;
        
        //System.out.println("\nPossible Moves Available: ");
        //for (int i = 0; i < PiecePossibleMoves.length; i++){
        //    if (PiecePossibleMoves[i] != -1) System.out.print(PiecePossibleMoves[i] + " ");
        //}

        while (true) {
            chosenMove = PiecePossibleMoves[rng.nextInt(8)];
            if (GameState.isMoveValid(chosenMove, PiecePossibleMoves)) break;
        }

        System.out.println("\nRandom Player Chose: " + chosenMove);

        return chosenMove;
    }
}
