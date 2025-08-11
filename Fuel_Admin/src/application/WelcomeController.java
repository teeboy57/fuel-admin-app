package application;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class WelcomeController {

    @FXML
    private void initialize() {
        // Automatically switch after 3 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> SceneManager.switchScene("login.fxml"));
        pause.play();
    }

    @FXML
    private void handleContinue() {
        SceneManager.switchScene("login.fxml");
    }
}
