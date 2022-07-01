package my.games.tron;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static my.games.tron.BaseBike.Direction;

public class GameEngine {
    private Canvas canvas;
    private GraphicsContext gc;
    private volatile boolean isRunning = false;
    private volatile boolean isPaused = false;

    private Grid grid = new Grid();
    private boolean singlePlayer = true;
    private BaseBike player1Bike = new PlayerBike(Grid.WIDTH - Point.SIZE, Grid.HEIGHT / 2, Color.BLUE, BaseBike.Direction.LEFT);
    private BaseBike player2Bike = new PlayerBike(0, Grid.HEIGHT / 2, Color.RED, BaseBike.Direction.RIGHT);
    private BaseBike cmp1Bike = new CmpBike(0, Grid.HEIGHT / 2, Color.RED, BaseBike.Direction.RIGHT);
    private List<BaseBike> bikes = new ArrayList<>();

    public GameEngine(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    public void initBikes() {
        bikes.add(player1Bike);
        if(singlePlayer) {
            bikes.add(cmp1Bike);
        }else {
            //multiplayer
            bikes.add(player2Bike);
        }
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

    public void startGame(boolean singlePlayer) {
        this.singlePlayer = singlePlayer;

        initBikes();
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
            player1Bike.setDirection(Direction.fromArrowKeyCode(event.getCode()));
        }else if(!singlePlayer && event.getCode().isLetterKey()) {
            Optional<Direction> direction = Direction.fromLetterKeyCode(event.getCode());
            direction.ifPresent(direction1 -> player2Bike.setDirection(direction1));
        }
    }
}
