package application;

import javafx.fxml.FXML;

public class ApiController {

    @FXML
    private void handleLogout() {
    
    	    SceneManager.switchScene("userprofile.fxml");
    	    SceneManager.switchScene("login.fxml"); // Restart the app or exit
    }
}
