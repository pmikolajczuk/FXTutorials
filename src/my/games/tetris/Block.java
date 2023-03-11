package my.games.tetris;

import javafx.scene.canvas.GraphicsContext;

import java.util.Collections;
import java.util.List;

public class Block {
    private final List<Brick> bricks = Collections.singletonList(new Brick(100, 100));

    private Block() {

    }

    public static Block createNewBlock() {
        return new Block();
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public void render(GraphicsContext gc) {
        bricks.forEach(brick -> brick.render(gc));
    }

    public void move(int moveX, int moveY, Bottom bottom) {
        if (bricks.stream().noneMatch(brick -> brick.isMoveColliding(moveX, moveY, bottom))) {
            bricks.forEach(brick -> brick.move(moveX, moveY));
        }
    }
}
