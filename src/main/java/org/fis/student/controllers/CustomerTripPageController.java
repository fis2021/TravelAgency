package org.fis.student.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis.student.model.Trip;
import org.fis.student.services.AdminTripService;

import java.io.IOException;

public class CustomerTripPageController {

    private static ObjectRepository<Trip> tripRepository = AdminTripService.getTripRepository();

    @FXML
    public ListView<String> trips = new ListView<>();

    @FXML
    public void initialize() throws IOException {
        ObservableList<String> items = FXCollections.observableArrayList ();
        for (Trip trip : tripRepository.find()) {
            items.add(trip.toString());
            trips.setItems(items);
        }
    }
    public void goToBookTripAction(javafx.event.ActionEvent addTrip) throws Exception{
        Parent addTripInterface = FXMLLoader.load(getClass().getClassLoader().getResource("book_trip.fxml"));
        Stage window = (Stage) ((Node) addTrip.getSource()).getScene().getWindow();;
        window.setTitle("Book a Trip");
        window.setScene(new Scene(addTripInterface, 600, 460));
        window.show();
    }
}
