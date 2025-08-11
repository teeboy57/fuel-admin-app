package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;

public class CarDetailsController {

    @FXML
    private ComboBox<String> brandComboBox;

    @FXML
    private ComboBox<String> modelComboBox;

    @FXML
    private Label fuelCapacityLabel;

    @FXML
    private Label fullTankPriceLabel;

    // Store brand -> list of models
    private Map<String, List<String>> brandModelsMap = new HashMap<>();

    // Fuel capacities for models (sample values)
    private Map<String, Double> fuelCapacities = new HashMap<>();

    private final double pricePerLitre = 23.50; // Static example fuel price

    @FXML
    private void initialize() {
        loadCarDataFromXML();

        // Populate brand combo box
        brandComboBox.setItems(FXCollections.observableArrayList(brandModelsMap.keySet()));

        brandComboBox.setOnAction(e -> {
            String selectedBrand = brandComboBox.getValue();
            modelComboBox.getItems().clear();
            fuelCapacityLabel.setText("Fuel Capacity: ");
            fullTankPriceLabel.setText("Full Tank Price: ");
            if (selectedBrand != null) {
                List<String> models = brandModelsMap.get(selectedBrand);
                if (models != null) {
                    modelComboBox.setItems(FXCollections.observableArrayList(models));
                }
            }
        });

        modelComboBox.setOnAction(e -> updateDetails());

        loadFuelCapacities();
    }

    private void loadCarDataFromXML() {
        try {
            var inputStream = getClass().getResourceAsStream("/application/cars.xml");
            if (inputStream == null) {
                throw new RuntimeException("cars.xml not found in resources.");
            }

            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(inputStream);

            doc.getDocumentElement().normalize();

            NodeList brandList = doc.getElementsByTagName("brand");
            for (int i = 0; i < brandList.getLength(); i++) {
                Element brandElement = (Element) brandList.item(i);
                String brandName = brandElement.getAttribute("name");

                List<String> models = new ArrayList<>();
                NodeList modelList = brandElement.getElementsByTagName("model");
                for (int j = 0; j < modelList.getLength(); j++) {
                    models.add(modelList.item(j).getTextContent().trim());
                }
                brandModelsMap.put(brandName, models);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading car data from XML.");
        }
    }

    private void loadFuelCapacities() {
        // Sample values, adjust as needed
        fuelCapacities.put("Hilux", 80.0);
        fuelCapacities.put("Fortuner", 70.0);
        fuelCapacities.put("Corolla", 50.0);
        fuelCapacities.put("Corolla Cross", 50.0);
        fuelCapacities.put("Yaris", 45.0);
        fuelCapacities.put("Land Cruiser", 90.0);
        fuelCapacities.put("Condor", 80.0);
        fuelCapacities.put("Polo", 45.0);
        fuelCapacities.put("Polo Vivo", 40.0);
        fuelCapacities.put("Golf", 50.0);
        fuelCapacities.put("T-Cross", 45.0);
        fuelCapacities.put("Tiguan", 60.0);
        fuelCapacities.put("Passat", 60.0);
        fuelCapacities.put("Amarok", 80.0);
        fuelCapacities.put("Ranger", 85.0);
        fuelCapacities.put("EcoSport", 50.0);
        fuelCapacities.put("Focus", 55.0);
        fuelCapacities.put("Fiesta", 45.0);
        fuelCapacities.put("Endura", 80.0);
        fuelCapacities.put("Everest", 75.0);
        fuelCapacities.put("Navara", 75.0);
        fuelCapacities.put("Magnite", 40.0);
        fuelCapacities.put("X-Trail", 60.0);
        fuelCapacities.put("Qashqai", 55.0);
        fuelCapacities.put("Juke", 40.0);
        fuelCapacities.put("Patrol", 90.0);
        fuelCapacities.put("D-Max", 80.0);
        fuelCapacities.put("MU-X", 70.0);
        fuelCapacities.put("KB", 75.0);
        fuelCapacities.put("3 Series", 60.0);
        fuelCapacities.put("5 Series", 65.0);
        fuelCapacities.put("X3", 70.0);
        fuelCapacities.put("X5", 80.0);
        fuelCapacities.put("7 Series", 70.0);
        fuelCapacities.put("A-Class", 45.0);
        fuelCapacities.put("C-Class", 60.0);
        fuelCapacities.put("E-Class", 70.0);
        fuelCapacities.put("S-Class", 80.0);
        fuelCapacities.put("GLA", 55.0);
        fuelCapacities.put("GLC", 60.0);
        fuelCapacities.put("GLS", 70.0);
        fuelCapacities.put("A3", 45.0);
        fuelCapacities.put("A4", 50.0);
        fuelCapacities.put("A6", 60.0);
        fuelCapacities.put("Q3", 55.0);
        fuelCapacities.put("Q5", 60.0);
        fuelCapacities.put("Q7", 70.0);
        fuelCapacities.put("Q8", 80.0);
        fuelCapacities.put("i10", 35.0);
        fuelCapacities.put("i20", 40.0);
        fuelCapacities.put("i30", 45.0);
        fuelCapacities.put("Tucson", 60.0);
        fuelCapacities.put("Santa Fe", 70.0);
        fuelCapacities.put("Creta", 50.0);
        fuelCapacities.put("Picanto", 35.0);
        fuelCapacities.put("Rio", 40.0);
        fuelCapacities.put("Seltos", 50.0);
        fuelCapacities.put("Sportage", 60.0);
        fuelCapacities.put("Carnival", 70.0);
        fuelCapacities.put("Kwid", 35.0);
        fuelCapacities.put("Sandero", 40.0);
        fuelCapacities.put("Kiger", 50.0);
        fuelCapacities.put("Duster", 60.0);
        fuelCapacities.put("Triber", 45.0);
        fuelCapacities.put("Tiggo 2 Pro", 45.0);
        fuelCapacities.put("Tiggo 4 Pro", 55.0);
        fuelCapacities.put("Omoda C5", 50.0);
        fuelCapacities.put("Omoda C9", 55.0);
        fuelCapacities.put("Jaecoo J7", 45.0);
        fuelCapacities.put("ATTO 3", 50.0);
        fuelCapacities.put("Shark", 55.0);
        fuelCapacities.put("SEALION 6", 60.0);
        fuelCapacities.put("SEALION 7", 60.0);
        fuelCapacities.put("Haval Jolion", 55.0);
        fuelCapacities.put("Haval H6", 60.0);
        fuelCapacities.put("GWM P-Series", 70.0);
        fuelCapacities.put("GWM Steed", 70.0);
        fuelCapacities.put("Punto", 40.0);
        fuelCapacities.put("500", 35.0);
        fuelCapacities.put("Panda", 35.0);
        fuelCapacities.put("Jazz", 40.0);
        fuelCapacities.put("Civic", 45.0);
        fuelCapacities.put("HR-V", 50.0);
        fuelCapacities.put("CR-V", 60.0);
        fuelCapacities.put("CX-3", 45.0);
        fuelCapacities.put("CX-5", 55.0);
        fuelCapacities.put("Mazda2", 40.0);
        fuelCapacities.put("Mazda3", 45.0);
        fuelCapacities.put("208", 40.0);
        fuelCapacities.put("3008", 50.0);
        fuelCapacities.put("5008", 55.0);
        fuelCapacities.put("Hunter", 60.0);
        fuelCapacities.put("Alsvin", 45.0);
        fuelCapacities.put("CS75 Pro", 60.0);
        fuelCapacities.put("Deepal S07", 55.0);
    }

    private void updateDetails() {
        String selectedModel = modelComboBox.getValue();
        if (selectedModel != null) {
            Double capacity = fuelCapacities.get(selectedModel);
            if (capacity != null) {
                double fullPrice = capacity * pricePerLitre;
                fuelCapacityLabel.setText("Fuel Capacity: " + capacity + " L");
                fullTankPriceLabel.setText("Full Tank Price: R " + String.format("%.2f", fullPrice));
            } else {
                fuelCapacityLabel.setText("Fuel Capacity: Data not available");
                fullTankPriceLabel.setText("Full Tank Price: Data not available");
            }
        }
    }

    @FXML
    private void handleBack() {
        SceneManager.switchScene("register.fxml");
    }

    @FXML
    private void handleDecline() {
        System.out.println("User declined fuel quote. Ending process.");
        SceneManager.switchScene("login.fxml");
    }

    @FXML
    private void handleAccept() {
        if (brandComboBox.getValue() == null || modelComboBox.getValue() == null) {
            showAlert("Please select both a car brand and model.");
            return;
        }
        SceneManager.switchScene("paymentsummary.fxml");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
