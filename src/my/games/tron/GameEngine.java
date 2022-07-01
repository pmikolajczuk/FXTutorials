package my.games.tron;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameEngine {
    private Canvas canvas;
    private GraphicsContext gc;
    private volatile boolean isRunning = false;
    private volatile boolean isPaused = false;

    private Grid grid = new Grid();
    private BaseBike player1Bike = new PlayerBike(Point.SIZE, Grid.HEIGHT / 2, Color.BLUE, BaseBike.Direction.RIGHT);
    private BaseBike cmp1Bike = new CmpBike(Grid.WIDTH - Point.SIZE, Grid.HEIGHT / 2, Color.RED, BaseBike.Direction.LEFT);
    private List<BaseBike> bikes = new ArrayList<>();


    public GameEngine(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        bikes.add(player1Bike);
        bikes.add(cmp1Bike);
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
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {

        List<Point> allTrails = bikes
                .stream()
                .flatMap(baseBike -> baseBike.getTrail().stream())
                .collect(Collectors.toList());
        bikes.forEach(bike -> bike.update(allTrails));

        if(bikes.stream().allMatch(BaseBike::isDead)) {
            stopGame();
        }
    }

    private void displayGame() {
        grid.render(gc);
        bikes.forEach(bike -> bike.render(gc));
    }

    public void startGame() {
        isRunning = true;
        new Thread(() -> gameLoop()).start();
    }

    public void stopGame() {
        isRunning = false;
    }

    public void handleKeyPressedEvent(KeyEvent event) {
        if(event.getCode() == KeyCode.SPACE) {
            isPaused = !isPaused;
        }else if(event.getCode().isArrowKey()) {
            player1Bike.setDirection(BaseBike.Direction.fromKeyCode(event.getCode()));
        }
    }
}
