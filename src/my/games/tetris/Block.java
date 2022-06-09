package my.games.tetris;

import javafx.scene.canvas.GraphicsContext;

public class Block {
    private Brick brick = new Brick(100, 100);

    public void render(GraphicsContext gc) {
        brick.render(gc);
    }

    public void move(int moveX, int moveY) {
        brick.move(moveX, moveY);
    }
}
