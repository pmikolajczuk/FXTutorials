package my.games.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BaseSnake implements Snake {
    Point head;
    LinkedList<Point> tail;
    int velocity = 1;
    volatile Direction direction = Direction.RIGHT;
    boolean hasFood = false;
    boolean isDead = false;

    public BaseSnake(int startX, int startY) {
        this(startX, startY, Color.CORNSILK);
    }

    public BaseSnake(int startX, int startY, Color color) {
        head = new Point(startX, startY, color);
        tail = createTail(4, color);
    }

    @Override
    public void update(List<Apple> apples, List<Point> obstacles) {
        //check collision with obstacles
        if (checkCollision(obstacles)) {
            isDead = true;
            return;
    }

        boolean isAppleEaten = checkAndEatApples(apples);
        updateTail(isAppleEaten);

        Point newHead = calculateNewHead(head, direction);
        //obstacles.removeIf(point -> point == head);
        obstacles.add(newHead);
        head = newHead;
    }

    @Override
    public void render(GraphicsContext gc) {
        head.render(gc);
        tail.forEach(point -> point.render(gc));
    }

    @Override
    public void setDirection(Direction newDirection) {
//        if (newDirection.isOpposite(direction)) {
//            return;
//        }
        direction = newDirection;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public List<Point> getBody() {
        LinkedList<Point> body = new LinkedList<>(tail);
        body.addFirst(head);
        return body;
    }

    protected Point calculateNewHead(Point currentHead, Direction direction) {
        Point newHead = null;
        switch (direction) {
            case UP:
                newHead = currentHead.translate(0, -velocity);
                break;
            case DOWN:
                newHead = currentHead.translate(0, velocity);
                break;
            case LEFT:
                newHead = currentHead.translate(-velocity, 0);
                break;
            case RIGHT:
                newHead = currentHead.translate(velocity, 0);
                break;
        }
        return newHead;
    }

    private boolean checkAndEatApples(List<Apple> apples) {
        List<Apple> eatenApples = apples.stream().filter(apple -> apple.equals(head)).collect(Collectors.toList());
        if (eatenApples.size() > 0) {
            apples.removeAll(eatenApples);
            return true;
        }
        return false;
    }

    private boolean checkCollision(List<Point> obstacles) {
        for (int i = 0; i < obstacles.size(); i++) {
            if (head.equals(obstacles.get(i)) && head != obstacles.get(i)) {
                Point collisionPoint = obstacles.get(i);
                collisionPoint.setColor(Color.RED);
                head.setColor(Color.RED);
                return (true);
            }
        }
        return false;
    }

    private void updateTail(boolean appleEaten) {
        //tail.addFirst(new Point(head.getX(), head.getY(), head.getColor()));
        tail.addFirst(head);
        if (appleEaten) {
            //skip removal of last element, snake will grow one element
            ;
        } else {
            tail.removeLast();
        }
    }

    private LinkedList<Point> createTail(int length, Color color) {
        LinkedList<Point> tail = new LinkedList<>();
        for (int i = 1; i <= length; i++) {
            tail.add(new Point(head.getX() - i, head.getY(), color));
        }
        return tail;
    }
}
