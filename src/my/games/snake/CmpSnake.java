package my.games.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class CmpSnake implements Snake {

    private final BaseSnake snake;

    public CmpSnake(int startX, int startY, Color color) {
        snake = new BaseSnake(startX, startY, color);
    }

    public void update(List<Apple> apples, List<Point> obstacles) {
        snake.setDirection(calculateNewDirection(apples, obstacles));
        snake.update(apples, obstacles);
    }

    public void render(GraphicsContext gc) {
        snake.render(gc);
    }

    @Override
    public void setDirection(Direction newDirection) {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    public boolean isDead() {
        return snake.isDead();
    }

    public List<Point> getBody() {
        return snake.getBody();
    }

    private BaseSnake.Direction calculateNewDirection(List<Apple> apples, List<Point> obstacles) {
        Apple nearestApple = null;
        int distanceToApple = Integer.MAX_VALUE;
        for (Apple apple : apples) {
            int newDistance = snake.head.distance(apple);
            if (newDistance < distanceToApple) {
                nearestApple = apple;
                distanceToApple = newDistance;
            }
        }

        BaseSnake.Direction nextDirection = snake.direction;

        //check if there are any apples
        if (nearestApple != null) {
            if (nearestApple.getX() - snake.head.getX() > 0) {
                nextDirection = BaseSnake.Direction.RIGHT;
            } else if (nearestApple.getX() - snake.head.getX() < 0) {
                nextDirection = BaseSnake.Direction.LEFT;
            } else if (nearestApple.getY() - snake.head.getY() > 0) {
                nextDirection = BaseSnake.Direction.DOWN;
            } else if (nearestApple.getY() - snake.head.getY() < 0) {
                nextDirection = BaseSnake.Direction.UP;
            }
        }

        nextDirection = avoidCollision(nextDirection, obstacles);

        return nextDirection;
    }

    private BaseSnake.Direction avoidCollision(BaseSnake.Direction nextDirection, List<Point> obstacles) {
        Point nextHead = snake.calculateNewHead(snake.head, nextDirection);

        if (checkCollision(nextHead, obstacles)) {
            int nextDirectionOrdinal = (nextDirection.ordinal() + 1) % 4;
            nextDirection = BaseSnake.Direction.values()[nextDirectionOrdinal];
            try {
                nextDirection = avoidCollision(nextDirection, obstacles);
            } catch (StackOverflowError e) {
                //if stack overflow then cmp lost
                return nextDirection;
            }
        }

        return nextDirection;
    }

    private boolean checkCollision(Point point, List<Point> obstacle) {
        for (int i = 0; i < obstacle.size(); i++) {
            if (point.equals(obstacle.get(i))) {
                return true;
            }
        }
        return false;
    }
}
