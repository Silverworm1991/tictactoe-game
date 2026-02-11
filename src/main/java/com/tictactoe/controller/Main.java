package com.tictactoe.controller;

import com.tictactoe.model.Board;
import com.tictactoe.view.GameView;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main Controller class for the Tic-Tac-Toe game. Handles user interactions, game flow, and
 * coordinates between Model and View. Implements JavaFX Application for desktop GUI functionality.
 */
public class Main extends Application {
  // Game state variables
  private char currentPlayer = 'X'; // Tracks whose turn it is ('X' = human, 'O' = computer)
  private Board board = new Board(); // Game logic and board state
  private GameView gameView = new GameView(); // UI components and layout
  private int humanScore = 0, computerScore = 0; // Score tracking across multiple games

  /** JavaFX Application entry point - sets up the main window and scene */
  @Override
  public void start(Stage stage) {
    // Initialize the game board UI
    setupBoard();

    // Create the main scene with specified dimensions
    Scene scene = new Scene(gameView.getRootLayout(), 450, 600);

    // Load CSS styling (handles gracefully if file not found)
    try {
      scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    } catch (Exception e) {
      System.out.println("CSS not found.");
    }

    // Configure and display the main window
    stage.setScene(scene);
    stage.setTitle("Pro Tic-Tac-Toe");
    stage.show();
  }

  /**
   * Creates the 3x3 grid of clickable cells for the game board. Each cell contains a transparent
   * Rectangle for sizing and a Text for X/O display.
   */
  private void setupBoard() {
    GridPane grid = gameView.getBoardGrid();
    grid.setDisable(false); // Enable user interaction
    grid.getChildren().clear(); // Remove any existing cells (for game reset)

    // Create 3x3 grid of cells
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        int row = i, col = j; // Final variables for lambda expression

        // Create individual cell as a StackPane (layered container)
        StackPane cell = new StackPane();
        cell.getStyleClass().add("cell"); // Apply CSS styling

        // Invisible rectangle for consistent cell sizing
        Rectangle border = new Rectangle(100, 100);
        border.setOpacity(0);

        // Text element to display X or O
        Text text = new Text("");
        text.setFont(Font.font(50));

        // Handle mouse clicks on this cell
        cell.setOnMouseClicked(e -> handlePlayerMove(row, col, text));

        // Layer the rectangle and text in the cell
        cell.getChildren().addAll(border, text);

        // Add cell to the grid at specified position
        grid.add(cell, col, row);
      }
    }
  }

  /**
   * Processes human player moves when they click on a cell. Validates move, updates UI, checks for
   * game end, then triggers computer move.
   */
  private void handlePlayerMove(int row, int col, Text text) {
    // Validate: human's turn, cell is empty, move is legal
    if (currentPlayer == 'X' && text.getText().isEmpty() && board.placeMove(row, col, 'X')) {
      // Update the cell with animation
      updateCell(text, 'X');

      // Check if game ends after human move
      if (checkGameOver('X')) return;

      // Switch to computer's turn and disable user input
      currentPlayer = 'O';
      gameView.getBoardGrid().setDisable(true);

      // Add delay before computer move for better UX
      PauseTransition pause = new PauseTransition(Duration.seconds(0.6));
      pause.setOnFinished(
          event -> {
            makeComputerMove();
          });
      pause.play();
    }
  }

  /**
   * Executes the computer's move using AI (Minimax algorithm). Updates the UI and checks for game
   * end conditions.
   */
  private void makeComputerMove() {
    // Get best move from AI algorithm
    int[] choice = board.getBestMove();
    if (choice[0] == -1) return; // No valid moves available

    // Place the move on the logical board
    board.placeMove(choice[0], choice[1], 'O');

    // Find the corresponding UI cell and update it
    for (Node node : gameView.getBoardGrid().getChildren()) {
      Integer r = GridPane.getRowIndex(node);
      Integer c = GridPane.getColumnIndex(node);

      // Match grid position with AI choice
      if (r != null && c != null && r == choice[0] && c == choice[1]) {
        StackPane cell = (StackPane) node;
        Text t = (Text) cell.getChildren().get(1); // Get text element
        updateCell(t, 'O'); // Update with animation
        break;
      }
    }

    // Check game state after computer move (on JavaFX thread)
    Platform.runLater(
        () -> {
          boolean gameOver = checkGameOver('O');
          if (!gameOver) {
            // Game continues - switch back to human player
            currentPlayer = 'X';
            gameView.getBoardGrid().setDisable(false); // Re-enable user input
          }
        });
  }

  /**
   * Updates a cell's visual appearance with smooth scaling animation. Sets the text content and
   * applies appropriate CSS styling.
   */
  private void updateCell(Text text, char player) {
    // Set the text content (X or O)
    text.setText(String.valueOf(player));

    // Apply CSS styling based on player
    text.getStyleClass().removeAll("text-x", "text-o");
    text.getStyleClass().add(player == 'X' ? "text-x" : "text-o");

    // Create smooth scaling animation from 0 to full size
    text.setScaleX(0);
    text.setScaleY(0);

    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), text);
    scaleTransition.setFromX(0);
    scaleTransition.setFromY(0);
    scaleTransition.setToX(1);
    scaleTransition.setToY(1);
    scaleTransition.setInterpolator(Interpolator.EASE_OUT); // Smooth easing
    scaleTransition.play();
  }

  /**
   * Creates an animated golden line through the winning combination. Calculates positions based on
   * grid layout and animates line drawing.
   */
  private void animateWinningLine(int[][] winningPositions) {
    if (winningPositions.length < 2) return; // Need at least 2 positions for a line

    // Calculate screen coordinates for line endpoints
    // Assumes 120px spacing between cells + 60px offset to center
    double startX = winningPositions[0][1] * 120 + 60;
    double startY = winningPositions[0][0] * 120 + 60;
    double endX = winningPositions[2][1] * 120 + 60;
    double endY = winningPositions[2][0] * 120 + 60;

    // Create line starting as a point (startX, startY)
    Line winLine = new Line(startX, startY, startX, startY);
    winLine.setStroke(Color.GOLD);
    winLine.setStrokeWidth(5);
    winLine.getStyleClass().add("winning-line"); // Apply CSS effects

    // Add line to the UI
    gameView.getRootLayout().getChildren().add(winLine);

    // Animate line drawing from start to end position
    Timeline timeline =
        new Timeline(
            new KeyFrame(
                Duration.ZERO,
                new KeyValue(winLine.endXProperty(), startX),
                new KeyValue(winLine.endYProperty(), startY)),
            new KeyFrame(
                Duration.millis(500),
                new KeyValue(winLine.endXProperty(), endX),
                new KeyValue(winLine.endYProperty(), endY)));
    timeline.play();
  }

  /**
   * Resets the game board with smooth fade out/in animation. Clears board state and rebuilds the
   * UI.
   */
  private void resetBoardWithAnimation() {
    GridPane grid = gameView.getBoardGrid();

    // Fade out the current board
    FadeTransition fadeOut = new FadeTransition(Duration.millis(300), grid);
    fadeOut.setFromValue(1.0);
    fadeOut.setToValue(0.0);

    // When fade out completes, reset and fade back in
    fadeOut.setOnFinished(
        e -> {
          // Reset game state
          board = new Board();
          currentPlayer = 'X';
          setupBoard(); // Rebuild the UI grid

          // Fade the new board back in
          FadeTransition fadeIn = new FadeTransition(Duration.millis(300), grid);
          fadeIn.setFromValue(0.0);
          fadeIn.setToValue(1.0);
          fadeIn.play();
        });
    fadeOut.play();
  }

  /**
   * Checks if the game has ended (win or draw) and handles end-game logic. Updates scores, shows
   * winning line animation, and displays game over dialog.
   */
  private boolean checkGameOver(char player) {
    String message = "";
    boolean over = false;

    // Check for winning line and animate it
    int[][] winningPositions = board.getWinningPositions(player);
    if (winningPositions.length > 0) {
      animateWinningLine(winningPositions);
    }

    // Determine game outcome
    if (board.checkWin(player)) {
      // Someone won
      if (player == 'X') {
        humanScore++;
        message = "You win! 🎉";
      } else {
        computerScore++;
        message = "AI wins! 🤖";
      }
      over = true;
    } else if (board.isFull()) {
      // Board is full with no winner
      message = "It's a draw! 🤝";
      over = true;
    }

    if (over) {
      // Update score display and disable further moves
      gameView.getScoreLabel().setText("You: " + humanScore + " | Computer: " + computerScore);
      gameView.getBoardGrid().setDisable(true);
      showGameOverAlert(message);
    }
    return over;
  }

  /** Displays game over dialog and initiates new game when dismissed. */
  private void showGameOverAlert(String message) {
    javafx.scene.control.Alert alert =
        new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
    alert.setTitle("Game Over");
    alert.setHeaderText(null);
    alert.setContentText(message);

    // Wait for user to click OK, then start new game
    alert.showAndWait();
    resetGame();
  }

  /** Initiates a new game with animated board reset. */
  private void resetGame() {
    resetBoardWithAnimation(); // Use animated version for smooth UX
  }

  /** Application entry point - launches the JavaFX application. */
  public static void main(String[] args) {
    launch(args);
  }
}
