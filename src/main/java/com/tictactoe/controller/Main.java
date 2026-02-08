package com.tictactoe.controller;

import com.tictactoe.model.Board;
import com.tictactoe.view.GameView;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
  private char currentPlayer = 'X';
  private Board board = new Board();
  private GameView gameView = new GameView(); // The new UI class
  private int humanScore = 0, computerScore = 0;

  @Override
  public void start(Stage stage) {
    // No button action setup needed anymore!
    setupBoard();

    Scene scene = new Scene(gameView.getRootLayout(), 450, 600);
    try {
      scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    } catch (Exception e) {
      System.out.println("CSS not found.");
    }

    stage.setScene(scene);
    stage.setTitle("Pro Tic-Tac-Toe");
    stage.show();
  }

  private void setupBoard() {
    GridPane grid = gameView.getBoardGrid();
    grid.setDisable(false);
    grid.getChildren().clear();

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        int row = i, col = j;
        StackPane cell = new StackPane();
        cell.getStyleClass().add("cell");

        Rectangle border = new Rectangle(100, 100);
        border.setOpacity(0);

        Text text = new Text("");
        text.setFont(Font.font(50));

        cell.setOnMouseClicked(e -> handlePlayerMove(row, col, text));
        cell.getChildren().addAll(border, text);
        grid.add(cell, col, row);
      }
    }
  }

  private void handlePlayerMove(int row, int col, Text text) {
    if (currentPlayer == 'X' && board.placeMove(row, col, 'X') && text.getText().isEmpty()) {
      updateCell(text, 'X');
      if (checkGameOver('X')) return;

      currentPlayer = 'O';
      gameView.getBoardGrid().setDisable(true);

      PauseTransition pause = new PauseTransition(Duration.seconds(0.6));
      pause.setOnFinished(
              event -> {
                makeComputerMove();
                // Only re-enable if the computer didn't just win or fill the board
                if (!board.checkWin('O') && !board.isFull()) {
                  gameView.getBoardGrid().setDisable(false);
                  currentPlayer = 'X';
                }
              });
      pause.play();
    }
  }
  private void makeComputerMove() {
    int[] choice = board.getBestMove();
    if (choice[0] == -1) return;

    board.placeMove(choice[0], choice[1], 'O');

    for (Node node : gameView.getBoardGrid().getChildren()) {
      Integer r = GridPane.getRowIndex(node);
      Integer c = GridPane.getColumnIndex(node);
      if (r != null && c != null && r == choice[0] && c == choice[1]) {
        StackPane cell = (StackPane) node;
        Text t = (Text) cell.getChildren().get(1);
        updateCell(t, 'O');
        break;
      }
    }
    checkGameOver('O');
  }

  private void updateCell(Text text, char player) {
    text.setText(String.valueOf(player));
    text.getStyleClass().removeAll("text-x", "text-o");
    text.getStyleClass().add(player == 'X' ? "text-x" : "text-o");
  }

  private boolean checkGameOver(char player) {
    String message = "";
    boolean over = false;

    if (board.checkWin(player)) {
      if (player == 'X') {
        humanScore++;
        message = "You win! 🎉";
      } else {
        computerScore++;
        message = "AI wins! 🤖";
      }
      over = true;
    } else if (board.isFull()) {
      message = "It's a draw! 🤝";
      over = true;
    }

    if (over) {
      gameView.getScoreLabel().setText("You: " + humanScore + " | Computer: " + computerScore);
      gameView.getBoardGrid().setDisable(true);
      showGameOverAlert(message);
    }
    return over;
  }

  private void showGameOverAlert(String message) {
    javafx.scene.control.Alert alert =
        new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
    alert.setTitle("Game Over");
    alert.setHeaderText(null);
    alert.setContentText(message);

    // This waits for the user to click OK
    alert.showAndWait();

    // Reset the game immediately after the toast is closed
    resetGame();
  }

  private void resetGame() {
    board = new Board();
    currentPlayer = 'X';
    setupBoard(); // Re-draws the board clean
  }

  public static void main(String[] args) {
    launch(args);
  }
}
