package org.fis.student.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fis.student.exceptions.DestinationAndDateExistsException;
import org.fis.student.exceptions.EmptyTextfieldsException;
import org.fis.student.services.AdminTripService;

public class DeleteTripController {

    @FXML
    private TextField destinationField;
    @FXML
    private TextField departure_dateField;
    @FXML
    private TextField return_dateField;
    @FXML
    private Text delete_tripMessage;

    @FXML
    public void handleDeleteTripAction() {
        try {
            AdminTripService.deleteTrip(destinationField.getText(), departure_dateField.getText(), return_dateField.getText());
            delete_tripMessage.setText("Trip deleted successfully!");
        } catch (EmptyTextfieldsException e){
            delete_tripMessage.setText(e.getMessage());
        } catch (DestinationAndDateExistsException e) {
            delete_tripMessage.setText(e.getMessage());
        }
    }

    @FXML
    public void goBackToTripPageAction(javafx.event.ActionEvent addTrip) throws Exception{
        Parent adminTripPageInterface = FXMLLoader.load(getClass().getClassLoader().getResource("admin_trip_page.fxml"));
        Stage window = (Stage) ((Node) addTrip.getSource()).getScene().getWindow();;
        window.setTitle("Trips");
        window.setScene(new Scene(adminTripPageInterface, 600, 460));
        window.show();
    }
}