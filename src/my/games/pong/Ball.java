package my.games.pong;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Ball {
    private static final Color color = Color.WHITE;
    private static final int SIZE = 20;
    private static final int BASE_SPEED = 2;

    private int posX, posY;
    private int speedX;
    private int speedY;

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Ball(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;

        //random direction at beginning
        if(new Random().nextBoolean()) {
            speedX = BASE_SPEED;
        } else {
            speedX = -BASE_SPEED;
        }
        if(new Random().nextBoolean()) {
            speedY = BASE_SPEED;
        } else {
            speedY = -BASE_SPEED;
        }
    }

    public boolean move(Paddle paddle1, Paddle paddle2, Score score) {
        boolean resetBall = detectCollision(paddle1, paddle2, score);

        posX += speedX;
        posY += speedY;

        return resetBall;
    }

    private boolean detectCollision(Paddle paddle1, Paddle paddle2, Score score) {
        //top collision
        if(posY <= 0) {
            speedY = -speedY;
            return false;
        }

        //bottom collision
        if(posY >= Grid.HEIGHT - SIZE) {
            speedY = -speedY;
            return false;
        }

        //paddle1 collision
        if (posX <= Paddle.WIDTH && posY >= paddle1.getPosY()
                && posY <= paddle1.getPosY() + Paddle.HEIGHT) {
            speedX = BASE_SPEED;
            return false;
        }

        //paddle2 collision
        if(posX + SIZE >= paddle2.getPosX() && posY >= paddle2.getPosY()
                && posY <= paddle2.getPosY() + Paddle.HEIGHT) {
            speedX = -BASE_SPEED;
            return false;
        }

        //out right
        if (posX > Grid.WIDTH - SIZE / 2) {
            score.increasePlayer1();
            return true;
        }

        //out left
        if (posX < SIZE / 2) {
            score.increasePlayer2();
            return true;
        }
        return false;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(posX, posY, SIZE, SIZE);
    }
}
