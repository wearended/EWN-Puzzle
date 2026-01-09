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

    GameState Game;
    String Name;
    
    public AIPlayer(String Name, GameState Game){
        this.Name = Name;
        this.Game = Game;
    }

    @Override
    public int choosePiece(boolean[] MovablePieces){
        int chosenPiece = 1;

        return chosenPiece;
    }

    @Override
    public int chooseMove(int[] PiecePossibleMoves, int ChosenPiece){
        int chosenMove = 0;
        
        System.out.println("\nAI Player Chose: " + chosenMove);

        return chosenMove;
    }
}
