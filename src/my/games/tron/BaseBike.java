package my.games.tron;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class BaseBike {
    private Color color;
    private int posX;
    private int posY;
    private int speed = 1;
    private List<Point> trail = new LinkedList<>();
    private PlayerBike.Direction direction;
    private boolean isDead = false;

    public BaseBike(int posX, int posY, Color color, PlayerBike.Direction direction) {
        this.posX = posX;
        this.posY = posY;
        this.color = color;
        this.direction = direction;
    }

    public List<Point> getTrail () {
        return trail;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDirection(PlayerBike.Direction direction) {
        this.direction = direction;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(color);
        trail.forEach(point -> point.render(gc));
        gc.fillRect(posX, posY, 1, 1);
    }

    public void update(List<Point> allTrails) {
        if (isDead) {
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
        checkCollision(allTrails);
    }

    public void checkCollision(List<Point> allTrails) {
        if (posX == 0 || posX == Grid.WIDTH || posY == 0 || posY == Grid.HEIGHT) {
            isDead = true;
        } else if (allTrails.contains(new Point(posX, posY))) {
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
