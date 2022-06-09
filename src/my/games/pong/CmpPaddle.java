package my.games.pong;

public class CmpPaddle extends Paddle{

    public CmpPaddle(int posX, int posY) {
        super(posX, posY);
    }

    @Override
    public void move(Ball ball) {
        if(ball.getPosY() > posY + HEIGHT / 2) {
            direction = Direction.DOWN;
        }else if(ball.getPosY() < posY + HEIGHT / 2)
            direction = Direction.UP;
        else{
            direction = Direction.NONE;
        }
        super.move(ball);
    }
}
