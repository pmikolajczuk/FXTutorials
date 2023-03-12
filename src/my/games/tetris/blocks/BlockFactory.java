package my.games.tetris.blocks;

import java.util.Random;

public class BlockFactory {
    public static Block createNewBlock() {
        int nextBlockShapeIndex = new Random().nextInt(BlockShape.values().length);
        BlockShape nextBlockShape = BlockShape.values()[nextBlockShapeIndex];
        return new Block(nextBlockShape.getShape());
    }
}
