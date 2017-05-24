package my.games.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Snake {
    //public static final Color COLOR = ;

    private Point head = new Point(10,10);
    private LinkedList<Point> tail = new LinkedList<>(Arrays.asList(new Point(9,10), new Point(9, 10),
            new Point(8, 10), new Point(7, 10), new Point(6, 10), new Point(5, 10)));
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
        checkCollision();
    }

    public void render(GraphicsContext gc) {
        head.render(gc);
        tail.forEach(point -> point.render(gc));
    }

    public boolean checkFood(Food food){
        if(head.equals(food.getPosition())){
            //eat food
            hasFood = true;
            return true;
        }
        return false;
    }

    private void checkCollision(){
        boolean result;

        List<Point> collisions =  tail.stream().filter(point -> point.equals(head)).collect(Collectors.toList());

        if(collisions.size() > 0) {
            collisions.forEach(point -> point.setColor(Color.RED));
            isDead = true;
        }
    }

    private void updateTail(){
        tail.addFirst(new Point(head.getX(), head.getY()));
        if(hasFood) {
            //skip removal of last element, snake will grow one element
            hasFood = false;
        }else{
            tail.removeLast();
        }
    }

    public void setDirection(Direction newDirection) {
        direction = newDirection;
    }

    public boolean isDead() {
        return isDead;
    }
}
