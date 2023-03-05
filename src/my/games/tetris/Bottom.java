package my.games.tetris;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Bottom {
    private static final int POSITION = Grid.HEIGHT;
    private final List<Brick> bricks = new ArrayList<>();

    public void addBlock(Block block) {
        bricks.addAll(block.getBricks());
    }

    public boolean isBlockColliding(Block block) {
        return block.getBricks()
                .stream()
                .anyMatch(this::isBrickColliding);
    }

    private boolean isBrickColliding(Brick brick) {
        if(brick.getPosY() + Brick.SIZE >= POSITION) {
            return true;
        }
        return false;
    }

    public void render(GraphicsContext gc) {
        bricks.forEach(brick -> brick.render(gc));
    }
}
