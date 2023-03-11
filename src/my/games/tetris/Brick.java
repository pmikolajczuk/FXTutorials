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

    public int getRightEdgeX() {
        return posX + SIZE;
    }

    public int getBottomEdgeY() {
        return posY + SIZE;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(COLOR);
        gc.fillRect(posX, posY, SIZE, SIZE);
    }

    public void move(int moveX, int moveY) {
        posX += moveX * Brick.SIZE;
        posY += moveY * Brick.SIZE;
    }

    public boolean isMoveColliding(int moveX, int moveY, Bottom bottom) {
        int newPosX = posX + moveX * Brick.SIZE;
        if (isNewPosCollidingWithWall(newPosX) || isNewPosCollidingWithBottom(newPosX, bottom)) {
            return true;
        }
        return false;
    }

    private boolean isNewPosCollidingWithWall(int newPosX) {
        return newPosX < 0 || newPosX + Brick.SIZE > Grid.WIDTH;
    }

    private boolean isNewPosCollidingWithBottom(int newPosX, Bottom bottom) {
        return bottom.getBricks().stream().anyMatch(brick -> isNewPosCollidingX(newPosX, brick));
    }

    private boolean isNewPosCollidingX(int newPosX, Brick brick) {
        if (this.posY == brick.posY && newPosX == brick.posX) {
            return true;
        }
        return false;
    }

    public boolean isCollidingY(Brick brick) {
        if (this.posX == brick.posX && (this.getBottomEdgeY() == brick.posY || this.getPosY() == brick.getBottomEdgeY())) {
            return true;
        }
        return false;
    }

}
