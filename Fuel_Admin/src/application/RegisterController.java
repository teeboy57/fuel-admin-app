package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField idField;
    @FXML private PasswordField passwordField;  // New password field
    @FXML private TextField addressField;
    @FXML private TextField carRegField;

    private final String homeAffairsFile = "home_affairs.txt";
    private final String trafficDbFile = "traffic_db.txt";
    private final String usersFile = "users.txt";

    @FXML
    private void handleBack() {
        SceneManager.switchScene("login.fxml");
    }

    @FXML
    private void handleNext() {
        // Make sure base files exist
        generateMockFilesIfNeeded();

        // Validate inputs
        if (!isFormValid()) return;

        // Save user
        saveUserToFile();

        // Add their ID and car reg to mock "databases"
        appendIfNotExists(homeAffairsFile, idField.getText().trim());
        appendIfNotExists(trafficDbFile, carRegField.getText().trim().toUpperCase());

        // Proceed to car selection
        SceneManager.switchScene("cardetails.fxml");
        saveLastUserId(idField.getText().trim());
    }

    private void saveLastUserId(String id) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("last_user_id.txt"))) {
            writer.write(id);
        } catch (IOException e) {
            showAlert("Failed to save last user ID.");
        }
    }

    private void generateMockFilesIfNeeded() {
        try {
            if (!Files.exists(Paths.get(homeAffairsFile))) {
                List<String> defaultIDs = Arrays.asList("9901015800087", "7503214500081", "8809026700082");
                Files.write(Paths.get(homeAffairsFile), defaultIDs);
            }

            if (!Files.exists(Paths.get(trafficDbFile))) {
                List<String> defaultRegs = Arrays.asList("CA123456", "GP234789", "EC009988");
                Files.write(Paths.get(trafficDbFile), defaultRegs);
            }
        } catch (IOException e) {
            showAlert("Error creating validation files.");
        }
    }

    private boolean isFormValid() {
        String name = nameField.getText().trim();
        String id = idField.getText().trim();
        String password = passwordField.getText();
        String address = addressField.getText().trim();
        String carReg = carRegField.getText().trim().toUpperCase();

        if (name.isEmpty() || id.isEmpty() || password.isEmpty() || address.isEmpty() || carReg.isEmpty()) {
            showAlert("All fields are required, including password.");
            return false;
        }

        if (!id.matches("\\d{13}")) {
            showAlert("ID Number must be exactly 13 digits.");
            return false;
        }

        if (!carReg.matches(".*(GP|NW|FS|NC|MP|WC|EC|KZN|L).*")) {
            showAlert("Car registration must include a valid SA province code.");
            return false;
        }

        return true;
    }

    private void appendIfNotExists(String filename, String value) {
        try {
            Path path = Paths.get(filename);

            // Create file if it doesn't exist
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            List<String> lines = Files.readAllLines(path);
            if (!lines.contains(value)) {
                Files.write(path, Collections.singletonList(value), StandardOpenOption.APPEND);
                System.out.println("Added '" + value + "' to " + filename);
            }
        } catch (IOException e) {
            showAlert("Failed to update " + filename);
        }
    }

    private void saveUserToFile() {
        String record = String.join(",",
                nameField.getText().trim(),
                idField.getText().trim(),
                passwordField.getText(),            // Save password here
                addressField.getText().trim(),
                carRegField.getText().trim().toUpperCase()
        );

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile, true))) {
            writer.write(record);
            writer.newLine();
        } catch (IOException e) {
            showAlert("Failed to save user data.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
