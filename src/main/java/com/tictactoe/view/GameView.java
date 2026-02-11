package com.tictactoe.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * GameView class handles the visual layout and UI components. Follows MVC pattern by separating
 * presentation from logic. Creates and manages all JavaFX UI elements.
 */
public class GameView {
  private VBox rootLayout; // Main container for all UI elements
  private GridPane boardGrid; // 3x3 grid container for game cells
  private Label scoreLabel; // Displays current score

  /**
   * Constructor sets up the complete UI layout. Creates a vertical layout with score display and
   * game board.
   */
  public GameView() {
    // Create main vertical container with generous spacing
    rootLayout = new VBox(30);
    rootLayout.setAlignment(Pos.CENTER);
    rootLayout.getStyleClass().add("root"); // Apply CSS background styling

    // Create score display label
    scoreLabel = new Label("You: 0 | Computer: 0");
    scoreLabel.setFont(Font.font("Arial", 26));
    scoreLabel.setTextFill(Color.WHITE);

    // Create grid container for the 3x3 game board
    boardGrid = new GridPane();
    boardGrid.setAlignment(Pos.CENTER);
    boardGrid.setHgap(10); // Horizontal spacing between cells
    boardGrid.setVgap(10); // Vertical spacing between cells

    // Add components to main layout (score on top, board below)
    rootLayout.getChildren().addAll(scoreLabel, boardGrid);
  }

  // === GETTER METHODS FOR CONTROLLER ACCESS ===

  /**
   * @return Main layout container for scene creation
   */
  public VBox getRootLayout() {
    return rootLayout;
  }

  /**
   * @return Game board grid for cell management
   */
  public GridPane getBoardGrid() {
    return boardGrid;
  }

  /**
   * @return Score label for updating display
   */
  public Label getScoreLabel() {
    return scoreLabel;
  }
}
