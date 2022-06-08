package my.games.pong;

public class Score {
    private int player1 = 0;
    private int player2 = 0;

    public int getPlayer1() {
        return player1;
    }

    public void setPlayer1(int player1) {
        this.player1 = player1;
    }

    public int getPlayer2() {
        return player2;
    }

    public void setPlayer2(int player2) {
        this.player2 = player2;
    }

    public void increasePlayer1() {
        player1++;
    }

    public void increasePlayer2() {
        player2++;
    }
}
