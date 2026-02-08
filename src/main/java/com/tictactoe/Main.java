package com.tictactoe;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Scanner scanner = new Scanner("UTF-8");
        // Note: Standard Scanner is fine for Linux
        Scanner input = new Scanner(System.in);
        char currentPlayer = 'X';
        boolean gameRunning = true;

        System.out.println("--- Welcome to Tic-Tac-Toe! ---");

        while (gameRunning) {
            board.printBoard();
            System.out.println("Player " + currentPlayer + ", enter row and column (0-2) separated by space: ");

            int row = input.nextInt();
            int col = input.nextInt();

            if (board.placeMove(row, col, currentPlayer)) {
                if (board.checkWin(currentPlayer)) {
                    board.printBoard();
                    System.out.println("Player " + currentPlayer + " wins! 🎉");
                    gameRunning = false;
                } else if (board.isFull()) {
                    board.printBoard();
                    System.out.println("It's a draw! 🤝");
                    gameRunning = false;
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            } else {
                System.out.println("Invalid move! Try again.");
            }
        }
        input.close();
    }
}