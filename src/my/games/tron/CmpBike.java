package my.games.tron;

import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

public class CmpBike extends BaseBike{

    public CmpBike(int posX, int posY, Color color, PlayerBike.Direction direction) {
        super(posX, posY, color, direction);
    }

    @Override
    public void update(List<Point> allTrails) {
        if(isDead) {
            return;
        }

        try{
            avoidCollision(posX, posY, allTrails);
            Point newTrailPoint = new Point(posX, posY);
            trail.add(newTrailPoint);
            allTrails.add(newTrailPoint);
        }catch (StackOverflowError e) {
            isDead = true;
        }
    }

    private void avoidCollision(int posX, int posY, List<Point> allTrails) {
        calculateNewPosition();
        if(isColliding(this.posX, this.posY, allTrails)) {
            this.posX = posX;
            this.posY = posY;
            calculateNewDirection();
            avoidCollision(this.posX, this.posY, allTrails);
        }else {
            return;
        }
    }

    private void calculateNewDirection() {
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            direction = calculateRandomDirection(Direction.UP, Direction.DOWN);
        } else {
            direction = calculateRandomDirection(Direction.RIGHT, Direction.LEFT);
        }
    }

    private Direction calculateRandomDirection(Direction direction1, Direction direction2) {
        if(new Random().nextBoolean()) {
            return direction1;
        }else {
            return direction2;
        }
    }
}
