package org.fis.student.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fis.student.exceptions.*;
import org.fis.student.services.CustomerBookingService;
import org.fis.student.services.UserService;

public class BookTripController {

    @FXML
    private Text bookTripMessage;
    @FXML
    private TextField destinationField;
    @FXML
    private TextField mentionsField;
    @FXML
    private TextField departure_dateField;
    @FXML
    private TextField return_dateField;

    @FXML
    public void handleBookTripAction() {
        try {
            CustomerBookingService.addReservation(destinationField.getText(), mentionsField.getText(), departure_dateField.getText(), return_dateField.getText());
            bookTripMessage.setText("Trip booked successfully!");
        } catch (EmptyTextfieldsException e){
            bookTripMessage.setText(e.getMessage());
        } catch (TripDoesNotExistException e) {
            bookTripMessage.setText(e.getMessage());
        }
    }

    @FXML
    public void goBackToTripPage(javafx.event.ActionEvent tripPage)throws Exception{
        Parent backToTripPage = FXMLLoader.load(getClass().getClassLoader().getResource("customer_trip_page.fxml"));
        Stage window = (Stage) ((Node) tripPage.getSource()).getScene().getWindow();;
        window.setTitle("Trips Page");
        window.setScene(new Scene(backToTripPage, 600, 460));
        window.show();
    }
}
