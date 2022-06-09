package my.games.pong;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Score {
    private final Color color = Color.WHITE;
    private int player1 = 0;
    private int player2 = 0;

    public void increasePlayer1() {
        player1++;
    }

    public void increasePlayer2() {
        player2++;
    }

    public void render(GraphicsContext gc) {
        gc.setStroke(color);
        gc.strokeText("Player 1: " + player1, 0, 20);
        gc.strokeText("Player 2: " + player2, Grid.WIDTH / 2, 20);
    }
}
