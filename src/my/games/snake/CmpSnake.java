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
        snake.setDirection(calculateNextDirection(apples, obstacles));
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

    private BaseSnake.Direction calculateNextDirection(List<Apple> apples, List<Point> obstacles) {
        Apple nearestApple = findNearestApple(apples);
        BaseSnake.Direction nextDirection = snake.direction;

        Point nextPoint = calculateNextPosition(snake.head, nearestApple, obstacles);
        if (nextPoint != null) {
            if (nextPoint.getX() - snake.head.getX() > 0) {
                nextDirection = BaseSnake.Direction.RIGHT;
            } else if (nextPoint.getX() - snake.head.getX() < 0) {
                nextDirection = BaseSnake.Direction.LEFT;
            } else if (nextPoint.getY() - snake.head.getY() > 0) {
                nextDirection = BaseSnake.Direction.DOWN;
            } else if (nextPoint.getY() - snake.head.getY() < 0) {
                nextDirection = BaseSnake.Direction.UP;
            }
        } else {
            nextDirection = avoidCollision(nextDirection, obstacles);
        }

        return nextDirection;
    }

    private Apple findNearestApple(List<Apple> apples) {
        Apple nearestApple = null;
        int distanceToApple = Integer.MAX_VALUE;
        for (Apple apple : apples) {
            int newDistance = snake.head.distance(apple);
            if (newDistance < distanceToApple) {
                nearestApple = apple;
                distanceToApple = newDistance;
            }
        }
        return nearestApple;
    }

    private Point calculateNextPosition(Point currentPosition, Point dest, List<Point> obstacles) {
        if (isColliding(currentPosition, obstacles)) {
            return null;
        }

        if (currentPosition.equals(dest)) {
            return currentPosition;
        }

        Point nextPosition;
        if (dest.getX() - currentPosition.getX() > 0) {
            nextPosition = new Point(currentPosition.getX() + 1, currentPosition.getY());
            if (calculateNextPosition(nextPosition, dest, obstacles) != null) {
                return nextPosition;
            }
        }
        if (dest.getX() - currentPosition.getX() < 0) {
            nextPosition = new Point(currentPosition.getX() - 1, currentPosition.getY());
            if (calculateNextPosition(nextPosition, dest, obstacles) != null) {
                return nextPosition;
            }

        }
        if (dest.getY() - currentPosition.getY() > 0) {
            nextPosition = new Point(currentPosition.getX(), currentPosition.getY() + 1);
            if (calculateNextPosition(nextPosition, dest, obstacles) != null) {
                return nextPosition;
            }
        }
        if (dest.getY() - currentPosition.getY() < 0) {
            nextPosition = new Point(currentPosition.getX(), currentPosition.getY() - 1);
            if (calculateNextPosition(nextPosition, dest, obstacles) != null) {
                return nextPosition;
            }
        }

        //return calculateNextPosition(currentPosition, dest, obstacles);
        return null;
    }

    private BaseSnake.Direction avoidCollision(BaseSnake.Direction nextDirection, List<Point> obstacles) {
        Point nextHead = snake.calculateNewHead(snake.head, nextDirection);

        if (isColliding(nextHead, obstacles)) {
            int nextDirectionOrdinal = (nextDirection.ordinal() + 1) % 4;
            nextDirection = BaseSnake.Direction.values()[nextDirectionOrdinal];
            try {
                nextDirection = avoidCollision(nextDirection, obstacles);
            } catch (StackOverflowError e) {
                //if stack overflow then stop recursion - there is no way out
                return nextDirection;
            }
        }

        return nextDirection;
    }

    private boolean isColliding(Point point, List<Point> obstacles) {
        for (int i = 0; i < obstacles.size(); i++) {
            if (point.equals(obstacles.get(i)) && point != obstacles.get(i)) {
                return true;
            }
        }
        return false;
    }
}