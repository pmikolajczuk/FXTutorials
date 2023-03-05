package my.games.tetris;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Grid {
    public static final Color COLOR = Color.BLACK;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 800;

    public void render(GraphicsContext gc) {
        gc.setFill(COLOR);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

    }
}
