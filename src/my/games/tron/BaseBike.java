package my.games.tron;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class BaseBike {
    protected Color color;
    protected int posX;
    protected int posY;
    protected List<Point> trail = new LinkedList<>();
    protected Direction direction;
    protected boolean isDead = false;

    public BaseBike(int posX, int posY, Color color, PlayerBike.Direction direction) {
        this.posX = posX;
        this.posY = posY;
        this.color = color;
        this.direction = direction;

        trail.add(new Point(posX, posY));
    }

    public List<Point> getTrail() {
        return trail;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDirection(PlayerBike.Direction direction) {
        if (!this.direction.isOpposite(direction)) {
            this.direction = direction;
        }
    }

    public void render(GraphicsContext gc) {
        gc.setFill(color);
        trail.forEach(point -> point.render(gc));
        gc.fillRect(posX, posY, Point.SIZE, Point.SIZE);
    }

    public abstract void update(List<Point> allTrails);

    protected void calculateNewPosition() {
        switch (direction) {
            case UP:
                posY -= Point.SIZE;
                break;
            case DOWN:
                posY += Point.SIZE;
                break;
            case LEFT:
                posX -= Point.SIZE;
                break;
            case RIGHT:
                posX += Point.SIZE;
                break;
        }
    }

    protected boolean isColliding(int posX, int posY, List<Point> allTrails) {
        if (posX == 0 || posX == Grid.WIDTH - Point.SIZE || posY == 0 || posY == Grid.HEIGHT - Point.SIZE) {
            return true;
        } else if (allTrails.contains(new Point(posX, posY))) {
            return true;
        }
        return false;
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        static {
            UP.opposite = DOWN;
            DOWN.opposite = UP;
            LEFT.opposite = RIGHT;
            RIGHT.opposite = LEFT;
        }

        private Direction opposite;

        private boolean isOpposite(Direction direction) {
            return opposite == direction;
        }

        public static Direction fromArrowKeyCode(KeyCode keyCode) {
            return Direction.valueOf(keyCode.name());
        }

        public static Optional<Direction> fromLetterKeyCode(KeyCode keyCode) {
            switch (keyCode) {
                case W:
                    return Optional.of(UP);
                case S:
                    return Optional.of(DOWN);
                case A:
                    return Optional.of(LEFT);
                case D:
                    return Optional.of(RIGHT);
                default:
                    return Optional.ofNullable(null);
            }
        }
    }
}
