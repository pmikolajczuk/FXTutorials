package my.games.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Grid {
    public static final Color COLOR = Color.BLACK;
    public static final int PIXEL_WIDTH = 800;
    public static final int PIXEL_HEIGHT = 600;
    public static final int POINT_SIZE = 20;
    public static final int WIDTH = PIXEL_WIDTH / POINT_SIZE;
    public static final int HEIGHT = PIXEL_HEIGHT / POINT_SIZE;



    public void update() {
        //TODO
    }

    public void render(GraphicsContext gc) {
        gc.setFill(COLOR);
        gc.fillRect(0, 0, PIXEL_WIDTH, PIXEL_HEIGHT);

        gc.setStroke(Color.CORNSILK);
        for (int x = 0; x <= WIDTH; x++) {
            for (int y = 0; y <= HEIGHT; y++){
                gc.strokeRect(x* POINT_SIZE, y* POINT_SIZE, 1, 1);
            }
        }
    }

}
