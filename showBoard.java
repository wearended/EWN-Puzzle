/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fopproject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ayniz
 */
public class showBoard {
        // Helper method to show the board
    static void showBoard(int[] piecePositions) {
        System.out.println("Current board:");
        for (int piece = 1; piece <= 6; piece++) {
            if (piecePositions[piece] != -1) {
                System.out.println("  Piece " + piece + " is at square " + piecePositions[piece]);
            }
        }
    }
    
    // Helper method to find which pieces can move based on dice
    static List<Integer> findMovablePieces(int dice, int[] piecePositions) {
        List<Integer> movable = new ArrayList<>();
        
        // Rule 1: Check if dice exactly matches a piece on board
        if (dice >= 1 && dice <= 6 && piecePositions[dice] != -1) {
            movable.add(dice);
            return movable;
        }
        
        // Rule 2: Find smallest piece bigger than dice
        int smallestBigger = -1;
        for (int piece = dice + 1; piece <= 6; piece++) {
            if (piecePositions[piece] != -1) {
                smallestBigger = piece;
                break;
            }
        }
        
        // Rule 3: Find biggest piece smaller than dice
        int biggestSmaller = -1;
        for (int piece = dice - 1; piece >= 1; piece--) {
            if (piecePositions[piece] != -1) {
                biggestSmaller = piece;
                break;
            }
        }
        
        if (smallestBigger != -1) movable.add(smallestBigger);
        if (biggestSmaller != -1) movable.add(biggestSmaller);
        
        return movable;
    }
    
    // Helper method to get all possible moves for given pieces
    static List<Move> getAllPossibleMoves(List<Integer> movablePieces, int[] piecePositions) {
        List<Move> moves = new ArrayList<>();
        
        // All 8 possible directions (like a king in chess)
        int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
        };
        
        for (int piece : movablePieces) {
            int currentPos = piecePositions[piece];
            if (currentPos == -1) continue; // Piece is captured
            
            int currentRow = currentPos / 10;
            int currentCol = currentPos % 10;
            
            // Check all 8 directions
            for (int[] dir : directions) {
                int newRow = currentRow + dir[0];
                int newCol = currentCol + dir[1];
                
                // Check if new position is inside the board (0-9 rows, 0-9 cols)
                if (newRow >= 0 && newRow < 10 && newCol >= 0 && newCol < 10) {
                    int newPos = newRow * 10 + newCol;
                    
                    // Cannot move to square 22 (the removed square)
                    if (newPos == 22) continue;
                    
                    // Create a move
                    moves.add(new Move(piece, newPos));
                }
            }
        }
        
        return moves;
    }
    
    // Helper method to apply a move to the board
    static void applyMove(Move move, int[] piecePositions) {
        int from = piecePositions[move.pieceNumber];
        
        System.out.print("Moving piece " + move.pieceNumber + 
                        " from square " + from + " to square " + move.destination);
        
        // Check if we're capturing another piece
        for (int piece = 1; piece <= 6; piece++) {
            if (piece != move.pieceNumber && piecePositions[piece] == move.destination) {
                System.out.println(" (Captured piece " + piece + "!)");
                piecePositions[piece] = -1; // Remove captured piece
                break;
            }
        }
        System.out.println();
        
        // Move the piece to new position
        piecePositions[move.pieceNumber] = move.destination;
    }
}

