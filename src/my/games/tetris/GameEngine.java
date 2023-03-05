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
    private long lastUpdateTime = 0L;

    private final Grid grid = new Grid();
    private final Bottom bottom = new Bottom();
    private Block currentBlock = Block.createNewBlock();

    public GameEngine(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    public void gameLoop() {
        while (isRunning) {
            if (!isPaused) {
                Platform.runLater(() -> {
                    updateGame();
                    displayGame();
                });
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        if (System.currentTimeMillis() - lastUpdateTime > 500) {
            doUpdateGame();
            lastUpdateTime = System.currentTimeMillis();
        }
    }

    private void doUpdateGame() {
        moveCurrentBlock(0, 1);
    }

    private void moveCurrentBlock(int moveX, int moveY) {
        currentBlock.move(moveX, moveY);
        if (bottom.isBlockColliding(currentBlock)) {
            bottom.addBlock(currentBlock);
            currentBlock = Block.createNewBlock();
        }
    }

    private void displayGame() {
        grid.render(gc);
        bottom.render(gc);
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
            case LEFT:
                moveCurrentBlock(-1, 0);
                break;
            case RIGHT:
                moveCurrentBlock(1, 0);
                break;
            case DOWN:
                moveCurrentBlock(0, 1);
            default:
                break;
        }
    }
}
