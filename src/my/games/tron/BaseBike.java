package my.games.tron;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class BaseBike {
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
    }

    public List<Point> getTrail() {
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
        gc.fillRect(posX, posY, Point.SIZE, Point.SIZE);
    }

    public void update() {
        if (isDead) {
            return;
        }

        trail.add(new Point(posX, posY));

    }

    public void checkForCollision(List<Point> allTrails) {
        if(isDead) {
            return;
        }
        calculateNewPosition();

        if (isColliding(posX, posY, allTrails)) {
            isDead = true;
        }
    }

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

//    private boolean isCollidingWithTrails(int posX, int posY, List<Point> allTrails) {
//        for(Point point : allTrails) {
//            if(posX)
//        }
//
//        return false;
//    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        public static Direction fromKeyCode(KeyCode keyCode) {
            return Direction.valueOf(keyCode.name());
        }
    }
}