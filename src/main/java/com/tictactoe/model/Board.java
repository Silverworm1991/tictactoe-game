package com.tictactoe.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
  private char[][] grid;

  public Board() {
    grid = new char[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        grid[i][j] = '-';
      }
    }
  }

  // --- Minimax Logic ---
  public int minimax(int depth, boolean isMax) {
    if (checkWin('O')) return 10 - depth;
    if (checkWin('X')) return depth - 10;
    if (isFull()) return 0;

    if (isMax) {
      int best = -1000;
      for (int[] move : getAvailableMoves()) {
        grid[move[0]][move[1]] = 'O';
        best = Math.max(best, minimax(depth + 1, false));
        grid[move[0]][move[1]] = '-';
      }
      return best;
    } else {
      int best = 1000;
      for (int[] move : getAvailableMoves()) {
        grid[move[0]][move[1]] = 'X';
        best = Math.min(best, minimax(depth + 1, true));
        grid[move[0]][move[1]] = '-';
      }
      return best;
    }
  }

  public int[] getBestMove() {
    int bestVal = -1000;
    int[] bestMove = {-1, -1};
    for (int[] move : getAvailableMoves()) {
      grid[move[0]][move[1]] = 'O';
      int moveVal = minimax(0, false);
      grid[move[0]][move[1]] = '-';
      if (moveVal > bestVal) {
        bestMove = move;
        bestVal = moveVal;
      }
    }
    return bestMove;
  }

  // --- Helper Methods ---
  public boolean placeMove(int row, int col, char s) {
    if (row >= 0 && row < 3 && col >= 0 && col < 3 && grid[row][col] == '-') {
      grid[row][col] = s;
      return true;
    }
    return false;
  }

  public boolean checkWin(char s) {
    for (int i = 0; i < 3; i++) {
      if ((grid[i][0] == s && grid[i][1] == s && grid[i][2] == s)
          || (grid[0][i] == s && grid[1][i] == s && grid[2][i] == s)) return true;
    }
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
        if (grid[i][j] == '-') moves.add(new int[] {i, j});
      }
    }
    return moves;
  }
}
