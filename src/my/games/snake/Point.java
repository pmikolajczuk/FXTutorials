package my.games.snake;

import javafx.scene.canvas.GraphicsContext;

public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point translate(int dx, int dy) {
        return new Point(x + dx, y + dy);
    }

    public void render(GraphicsContext gc){
        gc.fillRect(x*Grid.POINT_SIZE, y*Grid.POINT_SIZE, Grid.POINT_SIZE, Grid.POINT_SIZE);
    }

}
