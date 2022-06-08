package my.games.pong;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static my.games.pong.Paddle.Direction.NONE;

public class Paddle {
    private static final Color color = Color.WHITE;
    public static final int HEIGHT = 100;
    public static final int WIDTH = 20;

    private int posX;
    private int posY;
    private int speedY = 2;
    private Direction direction = NONE;

    public Paddle(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move() {
        switch (direction) {
            case UP:
                posY -= speedY;
                break;
            case DOWN:
                posY += speedY;
                break;
        }
        //direction = NONE;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(posX, posY, WIDTH, HEIGHT);
    }

    public enum Direction{
        UP, DOWN, NONE;
    }
}
