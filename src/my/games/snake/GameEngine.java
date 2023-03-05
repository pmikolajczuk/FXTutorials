package my.games.snake;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    public static final int NO_OF_CMP_SNAKES = 2;
    public static final int NO_OF_APPLES = 2;

    private final Canvas canvas;
    private final GraphicsContext gc;
    private volatile boolean isRunning = false;
    private volatile boolean isPaused = false;

    private final Grid grid = new Grid();
    private final List<Snake> snakes = new ArrayList<>();
    private final List<Apple> apples = new ArrayList<>();

    public GameEngine(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        //this.snakes.add(new BaseSnake(10, 20, Color.CORNSILK));

        int columns = 4;
        for (int i = 0; i < NO_OF_CMP_SNAKES; i++) {
            this.snakes.add(new CmpSnake((i % columns + 1) * 8, (i / columns + 1) * 6,
                    new Color(Math.random(), Math.random(), Math.random(), 1)));
        }

        for (int i = 0; i < NO_OF_APPLES; i++) {
            this.apples.add(Apple.createNewApple());
        }
    }

    public void startGame() {
        new Thread(() -> gameLoop()).start();
    }

    public void finishGame() {
        isRunning = false;
    }

    private void gameLoop() {
        isRunning = true;
        while (isRunning) {
            if (!isPaused) {
                Platform.runLater(() -> {
                    updateGame();
                    displayGame();
                });
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        List<Point> obstacles = new ArrayList<>();

        snakes.forEach(snake -> {
            obstacles.addAll(snake.getBody());
        });

        snakes.stream()
                .filter(snake -> !snake.isDead())
                .forEach(snake -> snake.update(apples, obstacles));

        while (apples.size() < NO_OF_APPLES) {
            apples.add(Apple.createNewApple(obstacles));
        }

        if (snakes.stream().allMatch(snake -> snake.isDead())) {
            isPaused = true;
        }
        grid.updateScore(snakes);
    }

    private void displayGame() {
        grid.render(gc);
        apples.forEach(apple -> apple.render(gc));
        snakes.forEach(snake -> snake.render(gc));
    }

    public void processInput(KeyEvent event) {
        if (event.getCode().isArrowKey()) {
            snakes.stream()
                    .filter(snake -> snake instanceof BaseSnake)
                    .forEach(snake -> snake.setDirection(Snake.Direction.fromKeyCode(event.getCode())));
        } else if (event.getCode() == KeyCode.SPACE) {
            isPaused = !isPaused;
        }
    }
}