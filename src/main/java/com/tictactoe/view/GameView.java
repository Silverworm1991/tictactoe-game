package com.tictactoe.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameView {
  private VBox rootLayout;
  private GridPane boardGrid;
  private Label scoreLabel;

  public GameView() {
    rootLayout = new VBox(30); // Increased spacing for a cleaner look
    rootLayout.setAlignment(Pos.CENTER);
    rootLayout.getStyleClass().add("root");

    scoreLabel = new Label("You: 0 | Computer: 0");
    scoreLabel.setFont(Font.font("Arial", 26));
    scoreLabel.setTextFill(Color.WHITE);

    boardGrid = new GridPane();
    boardGrid.setAlignment(Pos.CENTER);
    boardGrid.setHgap(10);
    boardGrid.setVgap(10);

    rootLayout.getChildren().addAll(scoreLabel, boardGrid);
  }

  public VBox getRootLayout() {
    return rootLayout;
  }

  public GridPane getBoardGrid() {
    return boardGrid;
  }

  public Label getScoreLabel() {
    return scoreLabel;
  }
}
