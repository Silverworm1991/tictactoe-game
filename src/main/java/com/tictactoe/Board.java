package com.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class Board {
  private char[][] grid;

  public Board() {
    grid = new char[3][3];
    // Fill the board with dashes to start
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        grid[i][j] = '-';
      }
    }
  }

  public void printBoard() {
    System.out.println("\n  0 1 2"); // Column headers
    for (int i = 0; i < 3; i++) {
      System.out.print(i + " "); // Row header
      for (int j = 0; j < 3; j++) {
        System.out.print(grid[i][j] + " ");
      }
      System.out.println();
    }
  }

  public boolean placeMove(int row, int col, char symbol) {
    if (row >= 0 && row < 3 && col >= 0 && col < 3 && grid[row][col] == '-') {
      grid[row][col] = symbol;
      return true;
    }
    return false;
  }

  public boolean checkWin(char s) {
    // Check rows and columns
    for (int i = 0; i < 3; i++) {
      if ((grid[i][0] == s && grid[i][1] == s && grid[i][2] == s)
          || (grid[0][i] == s && grid[1][i] == s && grid[2][i] == s)) return true;
    }
    // Check diagonals
    return (grid[0][0] == s && grid[1][1] == s && grid[2][2] == s)
        || (grid[0][2] == s && grid[1][1] == s && grid[2][0] == s);
  }

  public boolean isFull() {
    for (char[] row : grid) {
      for (char cell : row) if (cell == '-') return false;
    }
    return true;
  }

  public List<int[]> getAvailableMoves() {
    List<int[]> moves = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (grid[i][j] == '-') {
          moves.add(new int[] {i, j});
        }
      }
    }
    return moves;
  }
}
