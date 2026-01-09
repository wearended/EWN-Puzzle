
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Class to store game data
class GameData {
    int targetPiece;
    int[] diceSequence;
    int[] piecePositions; // Positions for pieces 1-6
    boolean loadedSuccessfully = false;
    
    GameData(String fileName) {
        try {
            Scanner file = new Scanner(new File(fileName));
            
            // Read target piece (first number in file)
            targetPiece = file.nextInt();
            
            // Read dice sequence (second line in file)
            String diceLine = file.nextLine(); // Skip to next line
            if (diceLine.trim().isEmpty()) {
                diceLine = file.nextLine(); // Get actual dice line
            }
            
            String[] diceNumbers = diceLine.split(" ");
            diceSequence = new int[diceNumbers.length];
            for (int i = 0; i < diceNumbers.length; i++) {
                diceSequence[i] = Integer.parseInt(diceNumbers[i]);
            }
            
            // Read piece positions (third line in file)
            piecePositions = new int[7]; // We'll use indexes 1-6
            for (int i = 1; i <= 6; i++) {
                if (file.hasNextInt()) {
                    piecePositions[i] = file.nextInt();
                } else {
                    piecePositions[i] = -1; // Not on board
                }
            }
            
            file.close();
            loadedSuccessfully = true;
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    int getDiceForTurn(int turnNumber) {
        if (turnNumber >= 0 && turnNumber < diceSequence.length) {
            return diceSequence[turnNumber];
        }
        return 1; // Default if out of bounds
    }
}

// Simple class to represent a move
class Move {
    int pieceNumber;
    int destination;
    
    Move(int pieceNumber, int destination) {
        this.pieceNumber = pieceNumber;
        this.destination = destination;
    }
    
    @Override
    public String toString() {
        return "Move piece " + pieceNumber + " to square " + destination;
    }
}

// Interface for players
interface SimplePlayer {
    Move chooseMove(List<Move> possibleMoves);
}

// Human player - you make the choices
class HumanPlayer implements SimplePlayer {
    Scanner input = new Scanner(System.in);
    
    @Override
    public Move chooseMove(List<Move> possibleMoves) {
        System.out.println("\nAvailable moves:");
        
        // Show all moves with numbers
        for (int i = 0; i < possibleMoves.size(); i++) {
            System.out.println((i + 1) + ". " + possibleMoves.get(i));
        }
        
        // Ask for choice
        int choice = 0;
        while (choice < 1 || choice > possibleMoves.size()) {
            System.out.print("Choose a move (1-" + possibleMoves.size() + "): ");
            try {
                choice = input.nextInt();
            } catch (Exception e) {
                input.nextLine(); // Clear wrong input
                System.out.println("Please enter a number!");
            }
        }
        
        return possibleMoves.get(choice - 1);
    }
}

// Random player - computer chooses randomly
class RandomPlayer implements SimplePlayer {
    Random random = new Random();
    
    @Override
    public Move chooseMove(List<Move> possibleMoves) {
        // Pick a random move
        int randomIndex = random.nextInt(possibleMoves.size());
        Move chosen = possibleMoves.get(randomIndex);
        
        System.out.println("\nComputer chose: " + chosen);
        return chosen;
    }
}