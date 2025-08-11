package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        SceneManager.setStage(primaryStage);
        
        primaryStage.setWidth(450);
        primaryStage.setHeight(400);
        primaryStage.setResizable(false);


        // ✅ Start from the welcome page
        SceneManager.switchScene("Welcome.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
