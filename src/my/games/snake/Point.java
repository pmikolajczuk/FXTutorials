package my.games.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Point {
    private final int x;
    private final int y;
    private Color color;


    public Point(int x, int y){
        this.x = x;
        this.y = y;
        this.color = null;
    }

    public Point(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point translate(int dx, int dy) {
        return new Point(Math.floorMod(x + dx, Grid.WIDTH), Math.floorMod(y + dy, Grid.HEIGHT), color);
    }

    public void render(GraphicsContext gc){
        gc.setFill(color);
        gc.fillRect(x*Grid.POINT_SIZE, y*Grid.POINT_SIZE, Grid.POINT_SIZE, Grid.POINT_SIZE);
    }

    public int distance(Point point){
       return Math.abs(point.x - this.x) + Math.abs(point.y - this.y);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }

        if(o instanceof Point){
            Point p = (Point)o;
            return x == p.x && y == p.y;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
