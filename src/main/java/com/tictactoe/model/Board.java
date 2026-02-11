package com.tictactoe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Board class represents the game state and AI logic for Tic-Tac-Toe. Contains the 3x3 grid, game
 * rules, and Minimax algorithm for unbeatable AI.
 */
public class Board {
  private char[][] grid; // 3x3 game board ('-' = empty, 'X' = human, 'O' = computer)

  /** Constructor initializes an empty 3x3 board. */
  public Board() {
    grid = new char[3][3];
    // Fill board with empty markers
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        grid[i][j] = '-';
      }
    }
  }

  // === MINIMAX ALGORITHM FOR UNBEATABLE AI ===

  /**
   * Minimax algorithm implementation for optimal move calculation. Recursively evaluates all
   * possible game states to find the best move.
   *
   * @param depth Current search depth (used for move preference)
   * @param isMax True if maximizing player's turn (computer), false for minimizing (human)
   * @return Evaluation score for the current board state
   */
  public int minimax(int depth, boolean isMax) {
    // Terminal conditions - game has ended
    if (checkWin('O')) return 10 - depth; // Computer wins (prefer quicker wins)
    if (checkWin('X')) return depth - 10; // Human wins (prefer later losses)
    if (isFull()) return 0; // Draw

    if (isMax) {
      // Computer's turn - maximize score
      int best = -1000;
      for (int[] move : getAvailableMoves()) {
        // Try this move
        grid[move[0]][move[1]] = 'O';
        // Recursively evaluate resulting position
        best = Math.max(best, minimax(depth + 1, false));
        // Undo move (backtrack)
        grid[move[0]][move[1]] = '-';
      }
      return best;
    } else {
      // Human's turn - minimize score (from computer's perspective)
      int best = 1000;
      for (int[] move : getAvailableMoves()) {
        // Try this move
        grid[move[0]][move[1]] = 'X';
        // Recursively evaluate resulting position
        best = Math.min(best, minimax(depth + 1, true));
        // Undo move (backtrack)
        grid[move[0]][move[1]] = '-';
      }
      return best;
    }
  }

  /**
   * Finds the optimal move for the computer using Minimax algorithm.
   *
   * @return Array containing [row, col] of best move, or [-1, -1] if no moves available
   */
  public int[] getBestMove() {
    int bestVal = -1000;
    int[] bestMove = {-1, -1};

    // Evaluate each possible move
    for (int[] move : getAvailableMoves()) {
      // Try the move
      grid[move[0]][move[1]] = 'O';
      // Calculate move value using Minimax
      int moveVal = minimax(0, false);
      // Undo the move
      grid[move[0]][move[1]] = '-';

      // Keep track of best move found so far
      if (moveVal > bestVal) {
        bestMove = move;
        bestVal = moveVal;
      }
    }
    return bestMove;
  }

  // === GAME LOGIC HELPER METHODS ===

  /**
   * Attempts to place a player's symbol at the specified position.
   *
   * @param row Row index (0-2)
   * @param col Column index (0-2)
   * @param s Player symbol ('X' or 'O')
   * @return True if move was successful, false if position is invalid/occupied
   */
  public boolean placeMove(int row, int col, char s) {
    if (row >= 0 && row < 3 && col >= 0 && col < 3 && grid[row][col] == '-') {
      grid[row][col] = s;
      return true;
    }
    return false;
  }

  /**
   * Checks if the specified player has won the game.
   *
   * @param s Player symbol to check ('X' or 'O')
   * @return True if player has three in a row (horizontal, vertical, or diagonal)
   */
  public boolean checkWin(char s) {
    // Check all rows and columns
    for (int i = 0; i < 3; i++) {
      // Check row i
      if ((grid[i][0] == s && grid[i][1] == s && grid[i][2] == s)
          ||
          // Check column i
          (grid[0][i] == s && grid[1][i] == s && grid[2][i] == s)) {
        return true;
      }
    }
    // Check diagonals
    return (grid[0][0] == s && grid[1][1] == s && grid[2][2] == s)
        || // Top-left to bottom-right
        (grid[0][2] == s && grid[1][1] == s && grid[2][0] == s); // Top-right to bottom-left
  }

  /**
   * Checks if the board is completely filled.
   *
   * @return True if no empty spaces remain
   */
  public boolean isFull() {
    for (char[] row : grid) {
      for (char cell : row) {
        if (cell == '-') return false; // Found empty space
      }
    }
    return true;
  }

  /**
   * Gets all empty positions on the board.
   *
   * @return List of [row, col] arrays representing available moves
   */
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

  /**
   * Finds the positions that form a winning line for the specified player. Used for drawing the
   * winning line animation.
   *
   * @param player Player symbol to check ('X' or 'O')
   * @return 2D array of winning positions, or empty array if no win
   */
  public int[][] getWinningPositions(char player) {
    // Check horizontal lines (rows)
    for (int i = 0; i < 3; i++) {
      if (grid[i][0] == player && grid[i][1] == player && grid[i][2] == player) {
        return new int[][] {{i, 0}, {i, 1}, {i, 2}};
      }
    }

    // Check vertical lines (columns)
    for (int j = 0; j < 3; j++) {
      if (grid[0][j] == player && grid[1][j] == player && grid[2][j] == player) {
        return new int[][] {{0, j}, {1, j}, {2, j}};
      }
    }

    // Check diagonal lines
    if (grid[0][0] == player && grid[1][1] == player && grid[2][2] == player) {
      return new int[][] {{0, 0}, {1, 1}, {2, 2}}; // Main diagonal
    }
    if (grid[0][2] == player && grid[1][1] == player && grid[2][0] == player) {
      return new int[][] {{0, 2}, {1, 1}, {2, 0}}; // Anti-diagonal
    }

    return new int[0][0]; // No winning combination found
  }
}
