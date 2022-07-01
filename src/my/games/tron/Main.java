package my.games.tron;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    private GameEngine engine;
    private Canvas canvas;
    private StartScreen startScreen = new StartScreen();
    private boolean isStartScreen = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tron game!");

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
        displayStartScreen();
    }

    public static void main(String[] args){
        launch(args);
    }

    private void initializeControls(){
        canvas.setOnKeyPressed(event -> handleKeyPressedEvent(event));
    }

    public void displayStartScreen() {
        isStartScreen = true;
        startScreen.render(canvas.getGraphicsContext2D());
    }

    private void handleKeyPressedEvent(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            restart();
        } else if(isStartScreen && event.getCode() == KeyCode.DIGIT1) {
            isStartScreen = false;
            engine.startGame(true);
        } else if(isStartScreen && event.getCode() == KeyCode.DIGIT2) {
            isStartScreen = false;
            engine.startGame(false);
        } else {
            engine.handleKeyPressedEvent(event);
        }
    }

    private void restart(){
        //restart
        engine.stopGame();
        engine = new GameEngine(canvas);
        displayStartScreen();
    }
}
