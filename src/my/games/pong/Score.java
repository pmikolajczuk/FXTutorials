package my.games.pong;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Score {
    private final Color color = Color.WHITE;
    private int player1 = 0;
    private int player2 = 0;

    public int getPlayer1() {
        return player1;
    }

    public int getPlayer2() {
        return player2;
    }

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

    public void renderWinner(GraphicsContext gc) {
        gc.setStroke(color);
        if(player1 > player2) {
            gc.strokeText("Player 1 WON !!!", Grid.WIDTH / 2 - 40, Grid.HEIGHT /4);
        }else {
            gc.strokeText("Player 2 WON !!!", Grid.WIDTH / 2 - 40, Grid.HEIGHT /4);
        }
    }

    public boolean isGameFinished()  {
        return (player1 == 10 || player2 == 10);
    }
}
