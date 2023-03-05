package my.games.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.List;

public interface Snake {
    int START_LENGTH = 5;

    void update(List<Apple> apples, List<Point> obstacles);

    void render(GraphicsContext gc);

    void setDirection(Direction newDirection);

    boolean isDead();

    List<Point> getBody();

    Color getColor();

    default int getScore() {
        return getBody().size() - START_LENGTH;
    }

    enum Direction {
        LEFT,
        DOWN,
        RIGHT,
        UP;

        static{
            LEFT.opposite = RIGHT;
            DOWN.opposite = UP;
            RIGHT.opposite = LEFT;
            UP.opposite = DOWN;
        }

        private Direction opposite;

        public boolean isOpposite(Direction direction){
            return this.opposite == direction;
        }

        public static Direction fromKeyCode(KeyCode keyCode) {
            return Direction.valueOf(keyCode.name());
        }
    }
}
