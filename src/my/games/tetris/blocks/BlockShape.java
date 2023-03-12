package my.games.tetris.blocks;

public enum BlockShape {
    SHAPE_1(BlockShapes.SHAPE_1),
    SHAPE_2(BlockShapes.SHAPE_2),
    SHAPE_3(BlockShapes.SHAPE_3),
    SHAPE_4(BlockShapes.SHAPE_4),
    SHAPE_5(BlockShapes.SHAPE_5),
    SHAPE_6(BlockShapes.SHAPE_6);

    private int[][] shape;

    BlockShape(int[][] shape) {
        this.shape = shape;
    }

    public int[][] getShape() {
        return shape;
    }
}
