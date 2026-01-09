import java.util.Scanner;

public class HumanPlayer extends Player{
    Scanner sc = new Scanner(System.in);

    public HumanPlayer(String PlayerName, GameState PlayerGame){
        this.Name = PlayerName;
        this.Game = PlayerGame;
    }

    @Override
    public String getName() {return Name;}

    @Override
    public int choosePiece(boolean[] MovablePieces){
        int chosenPiece;

        while (true) {
            System.out.print("Choose Piece to Move: ");
            chosenPiece = sc.nextInt() - 1;

            if (chosenPiece < 0 || chosenPiece + 1 > MovablePieces.length){
               System.out.println("That piece is not available!");
            }
            else if (!MovablePieces[chosenPiece]) {
                System.out.println("That piece is not available!");
            }
            else break;
        }
        return chosenPiece;
    }

    @Override
    public int chooseMove(int[] PiecePossibleMoves, int ChosenPiece){
        int chosenMove;
        
        System.out.println("Possible Moves Available: ");
        for (int i = 0; i < PiecePossibleMoves.length; i++){
            if (PiecePossibleMoves[i] != -1) System.out.print(PiecePossibleMoves[i] + " ");
        }
        System.out.println("");

        while (true) {
            System.out.print("Choose Where to Move: ");
            chosenMove = sc.nextInt();
            if (Game.isMoveValid(chosenMove, PiecePossibleMoves)) break;
            else System.out.println("Choose A Valid Move!");
        }

        return chosenMove;
    }
}
