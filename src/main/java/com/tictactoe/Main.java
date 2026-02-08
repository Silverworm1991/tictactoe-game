package com.tictactoe;

import java.util.List;
import java.util.Random;
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

    // ... inside the main method ...
    Random random = new Random();

    while (gameRunning) {
      board.printBoard();

      if (currentPlayer == 'X') {
        // Human Turn
        System.out.println("Your turn (X). Enter row and col (0-2): ");
        int row = input.nextInt();
        int col = input.nextInt();
        if (!board.placeMove(row, col, 'X')) {
          System.out.println("Invalid move! Try again.");
          continue;
        }
      } else {
        // Computer Turn
        System.out.println("Computer (O) is thinking...");
        List<int[]> moves = board.getAvailableMoves();
        int[] choice = moves.get(random.nextInt(moves.size()));
        board.placeMove(choice[0], choice[1], 'O');
        System.out.println("Computer placed O at: " + choice[0] + "," + choice[1]);
      }

      // Check Win/Draw
      if (board.checkWin(currentPlayer)) {
        board.printBoard();
        System.out.println((currentPlayer == 'X' ? "You win!" : "Computer wins!") + " 🎉");
        gameRunning = false;
      } else if (board.isFull()) {
        board.printBoard();
        System.out.println("It's a draw! 🤝");
        gameRunning = false;
      } else {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
      }
    }
  }
}
