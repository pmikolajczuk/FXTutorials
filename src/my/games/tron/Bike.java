package my.games.tron;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class Bike {
    private Color color;
    private int posX;
    private int posY;
    private int speed = 1;
    private List<Point> trail = new LinkedList<>();
    private Direction direction;
    private boolean isDead = false;

    public Bike(int posX, int posY, Color color, Direction direction) {
        this.posX = posX;
        this.posY = posY;
        this.color = color;
        this.direction = direction;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(color);
        trail.forEach(point -> point.render(gc));
        gc.fillRect(posX, posY, 1,1);
    }

    public void update() {
        if(isDead) {
            return;
        }

        trail.add(new Point(posX, posY));
        switch (direction) {
            case UP:
                posY -= speed;
                break;
            case DOWN:
                posY += speed;
                break;
            case LEFT:
                posX -= speed;
                break;
            case RIGHT:
                posX += speed;
                break;

        }
        checkCollision();
    }

    public void checkCollision() {
        if(trail.contains(new Point(posX, posY))) {
            isDead = true;
        }
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;
        public static Direction fromKeyCode(KeyCode keyCode) {
            return Direction.valueOf(keyCode.name());
        }
    }
}
