package my.games.pong;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{
    private GameEngine engine;
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Pong game!");

        StackPane root = new StackPane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        canvas = new Canvas(Grid.WIDTH, Grid.HEIGHT);
        canvas.setFocusTraversable(true);

        root.getChildren().add(canvas);
        engine = new GameEngine(canvas);

        primaryStage.setOnCloseRequest(event -> engine.stopGame());
        primaryStage.show();
        initializeControls();
        engine.startGame();
    }

    public static void main(String[] args){
        launch(args);
    }

    private void initializeControls(){
        canvas.setOnKeyPressed(event -> handleKeyPressedEvent(event));
        canvas.setOnKeyReleased(event -> engine.handleKeyReleasedEvent(event));
    }

    private void handleKeyPressedEvent(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            restart();
        } else {
            engine.handleKeyPressedEvent(event);
        }
    }

    private void restart(){
        //restart
        engine.stopGame();
        engine = new GameEngine(canvas);
        engine.startGame();
    }
}
