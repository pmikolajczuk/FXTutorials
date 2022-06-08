package my.games.pong;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Ball {
    private static final Color color = Color.WHITE;
    private static final int SIZE = 20;

    private int posX, posY;
    private int speedX =1;
    private int speedY =1;

    public Ball(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;

        //random direction at beginning
//        if(new Random().nextBoolean()) {
//            speedX = 1;
//        } else {
//            speedX = -1;
//        }
//        if(new Random().nextBoolean()) {
//            speedY = 1;
//        } else {
//            speedY = -1;
//        }
    }

    public boolean move(Paddle paddle1, Paddle paddle2, Score score) {
        //out right
        if (posX > Grid.WIDTH) {
            score.increasePlayer1();
            return true;
        }

        //out left
        if (posX + SIZE < 0) {
            score.increasePlayer2();
            return true;
        }

        //top collision
        if(posY <= 0) {
            speedY = -speedY;
        }

        //bottom collision
        if(posY >= Grid.HEIGHT - SIZE) {
            speedY = -speedY;
        }

        //paddle1 collision
        if (posX <= Paddle.WIDTH && posY >= paddle1.getPosY()
                && posY <= paddle1.getPosY() + Paddle.HEIGHT) {
            speedX = -speedX;
        }

        //paddle2 collision
        if(posX + SIZE >= paddle2.getPosX() && posY >= paddle2.getPosY()
                && posY <= paddle2.getPosY() + Paddle.HEIGHT) {
            speedX = -speedX;
        }

        posX += speedX;
        posY += speedY;

        return false;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(posX, posY, SIZE, SIZE);
    }
}
