package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.DateCell;


import java.time.LocalDate;

public class PaymentSummaryController {

    @FXML
    private Label quotationLabel;

    @FXML
    private Label discountLabel;

    @FXML
    private DatePicker repaymentDatePicker;

    @FXML
    private ComboBox<String> paymentOptionBox;

    // Dummy static data for this example
    private double fullTankAmount = 1000.00; // this could be passed from CarDetailsController
    private double discountPercent = 6.25;

    @FXML
    private void initialize() {
        double discountedAmount = fullTankAmount * (1 - discountPercent / 100);
        quotationLabel.setText("Total: R " + String.format("%.2f", discountedAmount));
        discountLabel.setText("Discount: " + discountPercent + "%");

        repaymentDatePicker.setValue(LocalDate.now().plusDays(1));
        repaymentDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()) || date.isAfter(LocalDate.now().plusDays(30)));
            }
        });

        paymentOptionBox.setItems(FXCollections.observableArrayList(
                "Debit Order", "Card Payment", "Google Pay"
        ));
    }

    @FXML
    private void handleBack() {
        SceneManager.switchScene("cardetails.fxml");
    }

    @FXML
    private void handlePayNow() {
        if (repaymentDatePicker.getValue() == null || paymentOptionBox.getValue() == null) {
            showAlert("Please choose a repayment date and payment method.");
            return;
        }

        SceneManager.switchScene("api.fxml");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    


        System.out.println("Processing payment...");
        System.out.println("Repayment Date: " + repaymentDatePicker.getValue());
        System.out.println("Payment Method: " + paymentOptionBox.getValue());

        // Proceed to next mock API page
        SceneManager.switchScene("api.fxml");
    }
}
