package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LoginController {

    @FXML private TextField usernameField;     // Here, username = ID Number
    @FXML private PasswordField passwordField;
    @FXML private CheckBox rememberMeCheckBox;

    private final String rememberFile = "remember_me.txt";
    private final String usersFile = "users.txt";

    @FXML
    private void initialize() {
        loadRememberedUsername();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("ID Number and password are required.");
            return;
        }

        if (authenticateUser(username, password)) {
            if (rememberMeCheckBox.isSelected()) {
                saveRememberedUsername(username);
            } else {
                clearRememberedUsername();
            }
            SceneManager.switchScene("cardetails.fxml");

        } else {
            showAlert("Invalid ID Number or password.");
        }
    }

    @FXML
    private void handleRegister() {
        SceneManager.switchScene("register.fxml");
    }

    @FXML
    private void handleForgotPassword() {
        SceneManager.switchScene("resetpassword.fxml");
    }

    private boolean authenticateUser(String idNumber, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // user record format: name,ID,password,address,carReg
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String storedId = parts[1].trim();
                    String storedPassword = parts[2].trim();

                    if (storedId.equals(idNumber) && storedPassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            showAlert("Error reading user data.");
        }
        return false;
    }

    private void saveRememberedUsername(String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rememberFile))) {
            writer.write(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearRememberedUsername() {
        try {
            Files.deleteIfExists(Paths.get(rememberFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRememberedUsername() {
        try {
            if (Files.exists(Paths.get(rememberFile))) {
                List<String> lines = Files.readAllLines(Paths.get(rememberFile));
                if (!lines.isEmpty()) {
                    String rememberedUsername = lines.get(0);
                    usernameField.setText(rememberedUsername);
                    rememberMeCheckBox.setSelected(true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Login Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
