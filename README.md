# Unbeatable Tic-Tac-Toe (JavaFX)

A modern, desktop Tic-Tac-Toe game built with JavaFX, featuring a clean MVC architecture and a "perfect" AI opponent.

## 🚀 Features
* **Unbeatable AI:** Implemented using the **Minimax Algorithm**, ensuring the computer never loses.
* **MVC Architecture:** Decoupled logic (Model), layout (View), and event handling (Controller) for clean, maintainable code.
* **Modern UI:** Styled with CSS including hover effects, rounded corners, and a responsive layout.
* **Smart Game Loop:** Automatic game resets and persistent score tracking.

## 🛠 Tech Stack
* **Java 17+**
* **JavaFX 17+**
* **Maven** (Build tool)

## 🎮 How to Play
1.  **Prerequisites:** Ensure you have Maven and a Java JDK installed.
2.  **Clone the repo:**
    ```bash
    git clone [https://github.com/your-username/tictactoe-game.git](https://github.com/your-username/tictactoe-game.git)
    cd tictactoe-game
    ```
3.  **Run the game:**
    ```bash
    mvn javafx:run
    ```

## 🧠 Behind the Logic
The AI evaluates every possible move using a recursive Minimax search. It assigns scores to board states (+10 for a win, -10 for a loss) and chooses the path that minimizes the player's maximum possible gain.