package my.games.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    public static final Color COLOR = Color.BLACK;
    //public static final Color SCORE_COLOR = Color.AQUA;
    public static final int PIXEL_WIDTH = 800;
    public static final int PIXEL_HEIGHT = 600;
    public static final int POINT_SIZE = 20;
    public static final int WIDTH = PIXEL_WIDTH / POINT_SIZE;
    public static final int HEIGHT = PIXEL_HEIGHT / POINT_SIZE;
    public static final int SCORE_PIXEL_HEIGHT = 100;
    public static final int SCORE_HEIGHT = SCORE_PIXEL_HEIGHT / POINT_SIZE;

    List<Integer> scores = new ArrayList<>();

    public void updateScore(List<Snake> snakes) {
        this.scores.clear();
        for(Snake snake : snakes) {
            this.scores.add(snake.getScore());
        }

    }

    public void render(GraphicsContext gc) {
        gc.setFill(COLOR);
        gc.fillRect(0, 0, PIXEL_WIDTH, PIXEL_HEIGHT + SCORE_PIXEL_HEIGHT);

        gc.setStroke(Color.CORNSILK);
        for (int x = 0; x <= WIDTH; x++) {
            for (int y = SCORE_HEIGHT; y <= HEIGHT + SCORE_HEIGHT; y++){
                gc.strokeRect(x* POINT_SIZE, y* POINT_SIZE, 1, 1);
            }
        }

        renderScore(gc);
    }

    private void renderScore(GraphicsContext gc) {
        int i = 1;
        int x = 0;
        int y = 1;
        for(Integer score : scores) {
            gc.strokeText("Snake " + i + ": " + score, x * 100, y * POINT_SIZE);
            i++;
            y++;
            if(y == 5) {
                x++;
                y = 1;
            }
        }
    }
}
