package com.tictactoe;

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
}