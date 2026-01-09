import java.util.Random;

public class RandomPlayer extends Player{
    // ============================================================
    // TODO: Implement chooseMove()
    // ------------------------------------------------------------
    // This method prompts the human player to choose the next move
    //
    // You may decide on the return type, parameters, and logic.
    // ============================================================

    // You may also add any other helper functions, variables,
    // and constructors needed for your implementation.

    Random rng;
    GameState Game;
    String Name;
    
    public RandomPlayer(String PlayerName, GameState PlayerGame){
        this.Name = PlayerName;
        this.Game = PlayerGame;
        this.rng = new Random();
    }

    @Override
    public String getName() {return Name;}

    @Override
    public int choosePiece(boolean[] MovablePieces){
        int chosenPiece;

        while (true) { 
            chosenPiece = rng.nextInt(6);
            if (MovablePieces[chosenPiece]) break;
        }

        System.out.println("Random Player Chose Piece #" + (chosenPiece + 1));

        return chosenPiece;
    }

    @Override
    public int chooseMove(int[] PiecePossibleMoves, int ChosenPiece){
        int chosenMove;
        
        while (true) {
            chosenMove = PiecePossibleMoves[rng.nextInt(8)];
            if (Game.isMoveValid(chosenMove, PiecePossibleMoves)) break;
        }

        System.out.println("Random Player Chose Move: " + chosenMove);

        return chosenMove;
    }
}