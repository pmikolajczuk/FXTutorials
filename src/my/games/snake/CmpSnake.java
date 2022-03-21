package my.games.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CmpSnake implements Snake {

    private BaseSnake snake;

    public CmpSnake(int startX, int startY, Color color) {
        snake = new BaseSnake(startX, startY, color);
    }

    public void update(List<Apple> apples, List<Point> obstacles) {
        snake.setDirection(calculateNextDirection(snake.direction, apples, obstacles));
        snake.update(apples, obstacles);
    }

    public void render(GraphicsContext gc) {
        snake.render(gc);
    }

    @Override
    public void setDirection(Direction newDirection) {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    @Override
    public boolean isDead() {
        return snake.isDead();
    }

    @Override
    public List<Point> getBody() {
        return snake.getBody();
    }

    @Override
    public Color getColor() {
        return snake.getColor();
    }


    private BaseSnake.Direction calculateNextDirection(BaseSnake.Direction currentDirection, List<Apple> apples, List<Point> obstacles) {
        Snake.Direction nextDirection = Optional
                .ofNullable(doCalculateNextDirection(snake.head, currentDirection, apples, obstacles))
                .orElse(currentDirection);

        return nextDirection;
    }
    private BaseSnake.Direction doCalculateNextDirection(
            Point currentHead,
            BaseSnake.Direction currentDirection,
            List<Apple> apples,
            List<Point> obstacles) {

        Apple nearestApple = findNearestApple(apples);
        BaseSnake.Direction nextDirection = currentDirection;

        Point nextPoint = calculateNextPosition(currentHead, nearestApple, obstacles);
        if (nextPoint != null) {
            if (nextPoint.getX() - currentHead.getX() > 0) {
                nextDirection = BaseSnake.Direction.RIGHT;
            } else if (nextPoint.getX() - currentHead.getX() < 0) {
                nextDirection = BaseSnake.Direction.LEFT;
            } else if (nextPoint.getY() - currentHead.getY() > 0) {
                nextDirection = BaseSnake.Direction.DOWN;
            } else if (nextPoint.getY() - currentHead.getY() < 0) {
                nextDirection = BaseSnake.Direction.UP;
            }
        }
        nextDirection = avoidCollision(nextDirection, obstacles);
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
        if(dest == null) {
            return null;
        }

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

        return null;
    }

    @Deprecated
    private BaseSnake.Direction avoidCollisionOLD(BaseSnake.Direction nextDirection, List<Point> obstacles) {
        Point nextHead = snake.calculateNewHead(snake.head, nextDirection);

        if (isColliding(nextHead, obstacles)) {
            int nextDirectionOrdinal = (nextDirection.ordinal() + 1) % 4;
            nextDirection = BaseSnake.Direction.values()[nextDirectionOrdinal];
            try {
                nextDirection = avoidCollisionOLD(nextDirection, obstacles);
            } catch (StackOverflowError e) {
                //if stack overflow then stop recursion - there is no way out
                e.printStackTrace();
                return nextDirection;
            }
        }

        return nextDirection;
    }

    private Snake.Direction avoidCollision(Snake.Direction nextDirection, List<Point> obstacles) {
        Point nextHead = snake.calculateNewHead(snake.head, nextDirection);

        if(isColliding(nextHead, obstacles)) {
            for(int i = 0; i < 4; i++) {
                nextDirection = increaseDirection(nextDirection, 1);
                nextHead = snake.calculateNewHead(snake.head, nextDirection);
                if (! isColliding(nextHead, obstacles)){
                    //non colliding direction found
                    return nextDirection;
                }
            }
            System.out.println("!!!!!!!!!!!!!!!!!!NO WAY OUT!!!!!!!!!!!!!!!!!!");
            return null;
        }

        return nextDirection;
    }

    private Snake.Direction increaseDirection(Snake.Direction nextDirection, int amount) {
        int nextDirectionOrdinal = (nextDirection.ordinal() + amount) % 4;
        nextDirection = Snake.Direction.values()[nextDirectionOrdinal];

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