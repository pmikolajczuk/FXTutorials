package my.games.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    private GameEngine engine;
    private Canvas canvas;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snake game !");

        StackPane root = new StackPane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        canvas = new Canvas(Grid.PIXEL_WIDTH, Grid.PIXEL_HEIGHT);
        canvas.setFocusTraversable(true);

        root.getChildren().add(canvas);
        engine = new GameEngine(canvas);

        primaryStage.setOnCloseRequest(event -> engine.finishGame());
        primaryStage.show();
        initializeControls();
        engine.startGame();
    }

    private void initializeControls(){
        canvas.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                restart();
            }else{
                engine.processInput(event);
            }
        });
    }

    private void restart(){
        //restart
        engine.finishGame();
        engine = new GameEngine(canvas);
        engine.startGame();
    }
}
