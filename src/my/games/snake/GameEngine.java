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

    private Canvas canvas;
    private GraphicsContext gc;
    private volatile boolean isRunning = false;
    private volatile boolean isPaused = false;

    private Grid grid = new Grid();
    private List<Snake> snakes = new ArrayList<>();
    private List<Apple> apples = new ArrayList<>();

    private List<Runnable> tasks = new ArrayList<>();

    public GameEngine(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        //this.snakes.add(new BaseSnake(10, 20, Color.CORNSILK));

        int rows = 4;
        for (int i = 0; i < NO_OF_CMP_SNAKES; i++) {
            this.snakes.add(new CmpSnake((i % rows + 1) * 8, (i / rows + 1) * 6,
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

    public synchronized void addTask(Runnable runnable) {
        tasks.add(runnable);
    }

    private void gameLoop() {
        isRunning = true;
        while (isRunning) {
            //processTasks();
            if (!isPaused) {
                Platform.runLater(() -> {
                    //if(isPaused) {return};
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

    private synchronized void processTasks() {
        tasks.forEach(runnable -> runnable.run());
        tasks.clear();
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