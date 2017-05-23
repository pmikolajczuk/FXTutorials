package my.games.snake;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameEngine {

    private Canvas canvas;
    private GraphicsContext gc;
    private boolean isRunning = false;

    private Grid grid;
    private Snake snake;

    public GameEngine(Canvas canvas){
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.grid = new Grid();
        this.snake = new Snake();
    }

    public void startGame(){
        initializeControls();
        new Thread(() -> gameLoop()).start();
    }

    public void finishGame(){
        isRunning = false;
    }

    public void gameLoop() {
        isRunning = true;
        while(isRunning){
            updateGame();
            displayGame();

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeControls(){
        canvas.setOnKeyPressed(event -> {
            if(event.getCode().isArrowKey()){
                snake.setDirection(Snake.Direction.fromKeyCode(event.getCode()));
            }
        });
    }

    private void updateGame(){
        grid.update();
        snake.update();
    }

    private void displayGame(){
        grid.render(gc);
        snake.render(gc);
    }
}
