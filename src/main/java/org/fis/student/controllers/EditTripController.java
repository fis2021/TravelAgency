package org.fis.student.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis.student.exceptions.*;
import org.fis.student.model.Trip;
import org.fis.student.services.AdminTripService;
import org.fis.student.services.UserService;

import java.util.Objects;

public class EditTripController {

    @FXML
    private TextField destinationField;
    @FXML
    private TextField departure_dateField;
    @FXML
    private TextField return_dateField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField number_of_free_spotsField;
    @FXML
    private Text edit_tripMessage;
    @FXML
    private TextField departure_dateField1;
    @FXML
    private TextField return_dateField1;

    private static ObjectRepository<Trip> tripRepository = AdminTripService.getTripRepository();

    @FXML
    public void handleEditTripAction() {
        try {
            AdminTripService.editTrip(destinationField.getText(), departure_dateField.getText(), return_dateField.getText(), descriptionField.getText(), priceField.getText(), number_of_free_spotsField.getText(), departure_dateField1.getText(), return_dateField1.getText());
            edit_tripMessage.setText("Trip edited successfully!");
        } catch (EmptyTextfieldsException e){
            edit_tripMessage.setText(e.getMessage());
        } catch (DestinationAndDateExistsException e) {
            edit_tripMessage.setText(e.getMessage());
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
