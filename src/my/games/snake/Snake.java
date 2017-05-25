package my.games.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Snake {
    public static final int START_X = 10;
    public static final int START_Y = 10;
    public static final Color COLOR = Color.CORNSILK;

    private Point head = new Point(START_X, START_Y, COLOR);
    private LinkedList<Point> tail = createTail(4, COLOR);
    private int velocity = 1;
    private volatile Direction direction = Direction.RIGHT;
    private boolean hasFood = false;
    private boolean isDead = false;

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;

        public static Direction fromKeyCode(KeyCode keyCode) {
            return Direction.valueOf(keyCode.name());
        }
    }

    public void update(List<Apple> apples) {
        boolean isAppleEaten = checkAndEatApples(apples);
        updateTail(isAppleEaten);
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
        checkCollision();
    }

    public void render(GraphicsContext gc) {
        head.render(gc);
        tail.forEach(point -> point.render(gc));
    }

    private boolean checkAndEatApples(List<Apple> apples) {
        List<Apple> eatenApples = apples.stream().filter(apple -> apple.equals(head)).collect(Collectors.toList());
        if (eatenApples.size() > 0) {
            apples.removeAll(eatenApples);
            return true;
        }
        return false;
    }

    private void checkCollision() {
        for (int i = 0; i < tail.size(); i++) {
            if (head.equals(tail.get(i))) {
                Point collisionPoint = tail.get(i);
                tail.set(i, new Point(collisionPoint.getX(), collisionPoint.getY(), Color.RED));
                isDead = true;
            }
        }
    }

    private void updateTail(boolean appleEaten){
        tail.addFirst(new Point(head.getX(), head.getY(), COLOR));
        if(appleEaten) {
            //skip removal of last element, snake will grow one element
            ;
        }else{
            tail.removeLast();
        }
    }

    private LinkedList<Point> createTail(int length, Color color){
        LinkedList<Point> tail = new LinkedList<>();
        for(int i = 1; i <= length; i++){
            tail.add(new Point(START_X - i, START_Y, color));
        }
        return tail;
    }

    public void setDirection(Direction newDirection) {
        direction = newDirection;
    }

    public boolean isDead() {
        return isDead;
    }
}
