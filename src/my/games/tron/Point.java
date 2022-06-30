package my.games.tron;

import javafx.scene.canvas.GraphicsContext;

import java.util.Objects;

public class Point {
    public static final int SIZE = 5;
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void render(GraphicsContext gc) {
        gc.fillRect(x, y, SIZE,SIZE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
