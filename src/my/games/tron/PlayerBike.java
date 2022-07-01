package my.games.tron;

import javafx.scene.paint.Color;

import java.util.List;

public class PlayerBike extends BaseBike{

    public PlayerBike(int posX, int posY, Color color, Direction direction) {
        super(posX, posY, color, direction);
    }

    @Override
    public void update(List<Point> allTrails) {
        if(isDead) {
            return;
        }
        calculateNewPosition();

        if (isColliding(posX, posY, allTrails)) {
            isDead = true;
        }else {
            Point newTrailPoint = new Point(posX, posY);
            trail.add(newTrailPoint);
            allTrails.add(newTrailPoint);

        }
    }
}
