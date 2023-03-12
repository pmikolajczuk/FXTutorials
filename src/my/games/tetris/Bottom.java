package my.games.tetris;

import javafx.scene.canvas.GraphicsContext;
import my.games.tetris.blocks.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bottom {
    private final List<Brick> bricks = new ArrayList<>();

    public List<Brick> getBricks() {
        return bricks;
    }

    public void addBlock(Block block) {
        bricks.addAll(block.getBricks());
        removeLinesIfNeeded();
    }

    private void removeLinesIfNeeded() {
        List<List<Brick>> linesToRemove = bricks.stream()
                .filter(brick -> brick.getPosX() == 0)
                .map(this::getBricksStartingFrom)
                .filter(bricks -> bricks.size() == getRowSize())
                .collect(Collectors.toList());

        linesToRemove.forEach(this::removeLine);
    }

    private List<Brick> getBricksStartingFrom(Brick brick) {
        return bricks.stream()
                .filter(b -> b.getPosY() == brick.getPosY())
                .collect(Collectors.toList());
    }

    private int getRowSize() {
        return Grid.WIDTH / Brick.SIZE;
    }

    private void removeLine(List<Brick> bricksLine) {
        bricks.removeAll(bricksLine);
        int removedLinePosY = bricksLine.stream().findAny().get().getPosY();
        moveBricksDown(removedLinePosY);
    }

    private void moveBricksDown(int removedLinePosY) {
        bricks.stream()
                .filter(brick -> brick.getPosY() < removedLinePosY)
                .forEach(brick -> brick.move(0, 1));
    }

    public boolean isBlockColliding(Block block) {
        return block.getBricks()
                .stream()
                .anyMatch(this::isBrickColliding);
    }

    private boolean isBrickColliding(Brick brick) {
        if(brick.getPosY() + Brick.SIZE >= Grid.HEIGHT) {
            return true;
        }
        return bricks.stream()
                .anyMatch(bottomBrick -> isBrickCollidingWithBottomBrick(brick, bottomBrick));
    }

    private boolean isBrickCollidingWithBottomBrick(Brick brick, Brick bottomBrick) {
        if (brick.getPosX() == bottomBrick.getPosX() && brick.getBottomEdgeY() == bottomBrick.getPosY()) {
            return true;
        }
        return false;
    }

    public void render(GraphicsContext gc) {
        bricks.forEach(brick -> brick.render(gc));
    }
}
