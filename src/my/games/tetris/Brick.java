package my.games.tetris;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick {
    public static final int SIZE = 50;
    private static final Color COLOR = Color.WHITE;

    private int posX;
    private int posY;

    public Brick(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(COLOR);
        gc.fillRect(posX, posY, SIZE, SIZE);
    }

    public void move(int moveX, int moveY) {
        posX += moveX * Brick.SIZE;
        posY += moveY * Brick.SIZE;
    }

}
