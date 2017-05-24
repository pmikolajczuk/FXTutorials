package my.games.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Food {
    public static final Color COLOR = Color.YELLOWGREEN;
    private Point position = generateNewPosition();

    public Point generateNewPosition(){
        Point position = new Point((int)(Math.random() * Grid.WIDTH), (int) (Math.random() * Grid.HEIGHT));
        position.setColor(COLOR);
        return position;
    }

    public void render(GraphicsContext gc){
        gc.setFill(COLOR);
        position.render(gc);
    }

    public Point getPosition() {
        return position;
    }
}
