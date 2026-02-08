package com.tictactoe;

public class Main {
    public static void main(String[] args) {
        Board gameBoard = new Board();

        System.out.println("Initial Board:");
        gameBoard.printBoard();

        System.out.println("\nPlacing 'X' at 1,1...");
        gameBoard.placeMove(1, 1, 'X');
        gameBoard.printBoard();
    }
}