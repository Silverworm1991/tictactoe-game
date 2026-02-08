package com.tictactoe;

import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
  private char currentPlayer = 'X';
  private Board board = new Board();
  private Random random = new Random();

  @Override
  public void start(Stage stage) {
    GridPane root = new GridPane();

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        int row = i;
        int col = j;

        StackPane cell = new StackPane();
        Rectangle border = new Rectangle(120, 120);
        border.setFill(Color.WHITE);
        border.setStroke(Color.DARKGRAY);

        Text text = new Text("");
        text.setFont(Font.font(60));

        cell.setOnMouseClicked(
            e -> {
              // HUMAN MOVE (Player X)
              if (currentPlayer == 'X'
                  && board.placeMove(row, col, 'X')
                  && text.getText().isEmpty()) {
                updateCell(text, 'X');

                if (checkGameOver('X')) return;

                // COMPUTER MOVE (Player O)
                currentPlayer = 'O';
                makeComputerMove(root);
              }
            });

        cell.getChildren().addAll(border, text);
        root.add(cell, col, row);
      }
    }

    stage.setScene(new Scene(root));
    stage.setTitle("Modern Tic-Tac-Toe");
    stage.show();
  }

  // HELPER METHODS (Must be outside start, but inside the class)
  private void makeComputerMove(GridPane root) {
    List<int[]> moves = board.getAvailableMoves();
    if (moves.isEmpty()) return;

    int[] choice = moves.get(random.nextInt(moves.size()));
    board.placeMove(choice[0], choice[1], 'O');

    for (Node node : root.getChildren()) {
      Integer r = GridPane.getRowIndex(node);
      Integer c = GridPane.getColumnIndex(node);

      // Check if this node matches the AI's choice
      if (r != null && c != null && r == choice[0] && c == choice[1]) {
        StackPane cell = (StackPane) node;
        Text t = (Text) cell.getChildren().get(1);
        updateCell(t, 'O');
        break;
      }
    }

    checkGameOver('O');
    currentPlayer = 'X';
  }

  private void updateCell(Text text, char player) {
    text.setText(String.valueOf(player));
    text.setFill(player == 'X' ? Color.DODGERBLUE : Color.TOMATO);
  }

  private boolean checkGameOver(char player) {
    if (board.checkWin(player)) {
      System.out.println(player + " wins!");
      return true;
    }
    if (board.isFull()) {
      System.out.println("Draw!");
      return true;
    }
    return false;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
