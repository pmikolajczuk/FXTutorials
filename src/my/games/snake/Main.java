package my.games.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snake game !");

        StackPane root = new StackPane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(Grid.PIXEL_WIDTH, Grid.PIXEL_HEIGHT);
        canvas.setFocusTraversable(true);

        root.getChildren().add(canvas);
        GameEngine engine = new GameEngine(canvas);

        primaryStage.setOnCloseRequest(event -> engine.finishGame());
        primaryStage.show();
        engine.startGame();
    }
}
