package my.games.snake;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameEngine {

    private Canvas canvas;
    private GraphicsContext gc;
    private volatile boolean isRunning = false;
    private volatile boolean isPaused = false;

    private Grid grid;
    private Snake snake;
    private List<Apple> apples;
    private int applesDefaultNumber;

    private List<Runnable> tasks;

    public GameEngine(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.grid = new Grid();
        this.snake = new Snake();
        this.apples = new ArrayList(Arrays.asList(Apple.createNewApple(), Apple.createNewApple()));
        this.applesDefaultNumber = apples.size();

        tasks = new ArrayList<>();
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
            processTasks();
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
        grid.update();
        snake.update(apples);

        while (apples.size() < applesDefaultNumber) {
            apples.add(Apple.createNewApple());
        }

        if (snake.isDead()) {
            isPaused = true;
        }
    }

    private void displayGame() {
        grid.render(gc);
        apples.forEach(apple -> apple.render(gc));
        snake.render(gc);
    }

    private synchronized void processTasks() {
        tasks.forEach(runnable -> runnable.run());
        tasks.clear();
    }

    public void processInput(KeyEvent event) {
        if (event.getCode().isArrowKey()) {
            snake.setDirection(Snake.Direction.fromKeyCode(event.getCode()));
        } else if (event.getCode() == KeyCode.SPACE) {
            isPaused = !isPaused;
        }
    }
}