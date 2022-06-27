package my.games.pong;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static my.games.pong.Paddle.Direction.NONE;

public class Paddle {
    private static final Color color = Color.WHITE;
    public static final int HEIGHT = 100;
    public static final int WIDTH = 20;

    protected int posX;
    protected int posY;
    protected int speedY = 1;
    protected Direction direction = NONE;

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

    public void move(Ball ball) {
        switch (direction) {
            case UP:
                posY = Math.max(0, posY -  speedY);
                break;
            case DOWN:
                posY = Math.min(Grid.HEIGHT - Paddle.HEIGHT, posY + speedY);
                break;
        }
    }

    public void render(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(posX, posY, WIDTH, HEIGHT);
    }

    public enum Direction{
        UP, DOWN, NONE;
    }
}
