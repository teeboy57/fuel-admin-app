package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static Stage primaryStage;

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void switchScene(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(SceneManager.class.getResource(fxmlFile));
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Fuel Administration Application");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
