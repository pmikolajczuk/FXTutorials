package my.games.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Snake {
    public static final Color COLOR = Color.CORNSILK;

    private Point head = new Point(10,10);
    private LinkedList<Point> tail = new LinkedList<>(Arrays.asList(new Point(9,10), new Point(9, 10),
            new Point(8, 10), new Point(7, 10), new Point(6, 10), new Point(5, 10)));
    private int velocity = 1;
    private Direction direction = Direction.RIGHT;

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;

        public static Direction fromKeyCode(KeyCode keyCode) {
            return Direction.valueOf(keyCode.name());
        }
    }

    public void update() {
        updateTail();
        switch (direction) {
            case UP:
                head = head.translate(0, -velocity);
                break;
            case DOWN:
                head = head.translate(0, velocity);
                break;
            case LEFT:
                head = head.translate(-velocity, 0);
                break;
            case RIGHT:
                head = head.translate(velocity, 0);
                break;
        }
    }

    public void render(GraphicsContext gc) {
        gc.setFill(COLOR);
        head.render(gc);
        tail.forEach(point -> point.render(gc));
    }

    public void setDirection(Direction newDirection) {
        direction = newDirection;
    }

    private void updateTail(){
        tail.addFirst(new Point(head.getX(), head.getY()));
        tail.removeLast();
    }
}
