package my.games.tetris;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class GameEngine {
    private Canvas canvas;
    private GraphicsContext gc;
    private volatile boolean isRunning = false;
    private volatile boolean isPaused = false;

    private Grid grid = new Grid();
    private Block currentBlock = new Block();

    public GameEngine(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    public void gameLoop() {
        while (isRunning) {
            if(!isPaused) {
                Platform.runLater(() -> {
                    updateGame();
                    displayGame();
                });
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        currentBlock.move(0, Brick.SIZE);
    }

    private void displayGame() {
        grid.render(gc);
        currentBlock.render(gc);
    }

    public void startGame() {
        isRunning = true;
        new Thread(() -> gameLoop()).start();
    }

    public void finishGame() {
        isRunning = false;
    }

    public void handleKeyPressedEvent(KeyEvent event) {
        switch (event.getCode()) {
        }
    }
}
