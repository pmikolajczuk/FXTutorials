package my.games.snake;

import javafx.scene.paint.Color;

import java.util.*;


public class Apple extends Point{
    public static final Color COLOR = Color.YELLOWGREEN;

    public Apple(int x, int y) {
        super(x, y, COLOR);
    }

    public static Apple createNewApple(){
        return new Apple((int)(Math.random() * Grid.WIDTH), (int) (Math.random() * Grid.HEIGHT));
    }
}
