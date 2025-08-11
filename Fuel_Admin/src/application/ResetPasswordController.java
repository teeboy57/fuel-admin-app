package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class ResetPasswordController {

    @FXML private TextField idField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;

    private final String usersFile = "users.txt";

    @FXML
    private void handleBack() {
        SceneManager.switchScene("login.fxml");
    }

    @FXML
    private void handleReset() {
        String id = idField.getText().trim();
        String newPass = newPasswordField.getText().trim();
        String confirmPass = confirmPasswordField.getText().trim();

        // --- Validation ---
        if (id.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }

        if (!id.matches("\\d{13}")) {
            showAlert("ID must be 13 digits.");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            showAlert("Passwords do not match.");
            return;
        }

        if (!Files.exists(Paths.get(usersFile))) {
            showAlert("No user database found.");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(usersFile));
            boolean found = false;

            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                // Match ID even if there are extra spaces
                if (parts.length >= 2 && parts[1].trim().equals(id)) {
                    // Ensure we have at least 3 columns (so password can be stored)
                    if (parts.length >= 3) {
                        parts[2] = newPass; // Update password
                    } else {
                        // If there was no password column, add it
                        parts = Arrays.copyOf(parts, 3);
                        parts[2] = newPass;
                    }
                    lines.set(i, String.join(",", parts));
                    found = true;
                    break;
                }
            }

            if (found) {
                Files.write(Paths.get(usersFile), lines);
                showAlert("Password reset successfully.");
                SceneManager.switchScene("login.fxml");
            } else {
                showAlert("No user found with that ID.");
            }

        } catch (IOException e) {
            showAlert("Error reading user database.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Password Reset");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
