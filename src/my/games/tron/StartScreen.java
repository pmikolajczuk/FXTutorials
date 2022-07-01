package my.games.tron;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class StartScreen {

    public void render(GraphicsContext gc) {
        gc.setFill(Grid.COLOR);
        gc.fillRect(0,0, Grid.WIDTH, Grid.HEIGHT);

        gc.setFill(Color.WHITE);
        gc.fillText("Enter no of players (1-2)", Grid.WIDTH / 2 - 100, Grid.HEIGHT / 2);
    }
}
