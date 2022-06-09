package my.games.pong;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class GameEngine {
    private Canvas canvas;
    private GraphicsContext gc;
    private volatile boolean isRunning = false;
    private volatile boolean isPaused = false;

    private Score score = new Score();
    private Grid grid = new Grid();
    private Ball ball = new Ball(Grid.WIDTH / 2, Grid.HEIGHT / 2);
    //private Paddle paddle1 = new Paddle(0,Grid.HEIGHT / 2);
    private Paddle paddle1 = new CmpPaddle(0,Grid.HEIGHT / 2);
    private Paddle paddle2 = new Paddle(Grid.WIDTH - Paddle.WIDTH, Grid.HEIGHT / 2);

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
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        paddle1.move(ball);
        paddle2.move(ball);
        if(ball.move(paddle1, paddle2, score)) {
            ball = new Ball(Grid.WIDTH / 2, Grid.HEIGHT / 2);
        };
    }

    private void displayGame() {
        grid.render(gc);
        score.render(gc);
        paddle1.render(gc);
        paddle2.render(gc);
        ball.render(gc);
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
            case W:
                paddle1.setDirection(Paddle.Direction.UP);
                break;
            case S:
                paddle1.setDirection(Paddle.Direction.DOWN);
                break;
            case UP:
                paddle2.setDirection(Paddle.Direction.UP);
                break;
            case DOWN:
                paddle2.setDirection(Paddle.Direction.DOWN);
                break;
            case SPACE:
                isPaused = !isPaused;
                break;
        }
    }

    public void handleKeyReleasedEvent(KeyEvent event) {
        switch(event.getCode()) {
            case W:
            case S:
                paddle1.setDirection(Paddle.Direction.NONE);
                break;
            case UP:
            case DOWN:
                paddle2.setDirection(Paddle.Direction.NONE);
                break;
        }
    }
}
