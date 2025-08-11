package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserProfileController {

    @FXML private Label nameLabel;
    @FXML private Label idLabel;
    @FXML private Label addressLabel;
    @FXML private Label carRegLabel;

    @FXML
    private void initialize() {
        loadLastUser();
    }

    private void loadLastUser() {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            String lastLine = null;

            while ((line = reader.readLine()) != null) {
                lastLine = line; // keep the last one
            }

            if (lastLine != null) {
                String[] parts = lastLine.split(",");
                if (parts.length == 4) {
                    nameLabel.setText("Name: " + parts[0]);
                    idLabel.setText("ID Number: " + parts[1]);
                    addressLabel.setText("Address: " + parts[2]);
                    carRegLabel.setText("Car Reg: " + parts[3]);
                }
            } else {
                nameLabel.setText("No users found.");
            }
        } catch (IOException e) {
            nameLabel.setText("Error reading user file.");
        }
    }

    @FXML
    private void handleBack() {
        SceneManager.switchScene("login.fxml");
    }
}
