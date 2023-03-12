package my.games.tetris.blocks;

import javafx.scene.canvas.GraphicsContext;
import my.games.tetris.Bottom;
import my.games.tetris.Brick;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private static final int START_X = 1;
    private final List<Brick> bricks = new ArrayList<>();

    Block(int[][] shape) {
        init(shape);
    }

    protected void init(int[][] shape) {
        for(int i = 0; i < shape.length; i++) {
            for(int j = 0; j < shape[i].length; j++) {
                if(shape[i][j] != 0) {
                    bricks.add(new Brick((j+START_X) * Brick.SIZE, i * Brick.SIZE));
                }
            }
        }
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
