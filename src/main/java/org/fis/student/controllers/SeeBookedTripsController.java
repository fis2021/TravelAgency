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
import org.fis.student.model.Reservation;
import org.fis.student.model.Trip;
import org.fis.student.services.AdminTripService;
import org.fis.student.services.CustomerBookingService;
import org.fis.student.services.CustomerListMytripsService;

import java.io.IOException;

public class SeeBookedTripsController {

    private static ObjectRepository<Reservation> bookedTripRepository = CustomerBookingService.getBookingRepository();

    @FXML
    public ListView<String> bookedTrips = new ListView<>();

    @FXML
    public void initialize() throws IOException {
        ObservableList<String> items = FXCollections.observableArrayList ();
        for (Reservation trip : bookedTripRepository.find()) {
            items.add(trip.toString());
            bookedTrips.setItems(items);
        }
    }

    public void goBackToTripPageAction(javafx.event.ActionEvent addTrip) throws Exception{
        Parent adminTripPageInterface = FXMLLoader.load(getClass().getClassLoader().getResource("admin_trip_page.fxml"));
        Stage window = (Stage) ((Node) addTrip.getSource()).getScene().getWindow();;
        window.setTitle("Trips");
        window.setScene(new Scene(adminTripPageInterface, 600, 460));
        window.show();
    }
}
