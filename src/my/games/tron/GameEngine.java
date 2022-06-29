package my.games.tron;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private Canvas canvas;
    private GraphicsContext gc;
    private volatile boolean isRunning = false;
    private volatile boolean isPaused = false;

    private Grid grid = new Grid();
    private Bike bike1 = new Bike(0, Grid.HEIGHT / 2, Color.BLUE, Bike.Direction.RIGHT);
    private List<Bike> bikes = new ArrayList<>();


    public GameEngine(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        bikes.add(bike1);
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
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        bikes.forEach(bike -> bike.update());

        if(bikes.stream().allMatch(Bike::isDead)) {
            stopGame();
        }
    }

    private void displayGame() {
        grid.render(gc);
        bikes.forEach(bike -> bike.render(gc));
    }

    public void startGame() {
        isRunning = true;
        //grid.render(gc);
        new Thread(() -> gameLoop()).start();
    }

    public void stopGame() {
        isRunning = false;
    }

    public void handleKeyPressedEvent(KeyEvent event) {
        if(event.getCode() == KeyCode.SPACE) {
            isPaused = !isPaused;
        }else if(event.getCode().isArrowKey()) {
            bike1.setDirection(Bike.Direction.fromKeyCode(event.getCode()));
        }
    }
}
