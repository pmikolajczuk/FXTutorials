package my.games.snake;

import javafx.scene.paint.Color;

public class Score {
    private int score;
    private Color color;

    public Score(int score, Color color) {
        this.score = score;
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
