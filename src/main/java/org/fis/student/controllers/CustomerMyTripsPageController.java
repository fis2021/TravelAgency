package org.fis.student.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.Objects;

public class CustomerMyTripsPageController {

    private static ObjectRepository<Reservation> reservationRepository = CustomerBookingService.getBookingRepository();
    private static String loggedUser;

    @FXML
    public ListView<String> myTrips = new ListView<>();

    @FXML
    public void initialize() throws IOException {
        loggedUser = LoginController.getLoggedUser();
        ObservableList<String> items = FXCollections.observableArrayList ();
        for (Reservation reservation : reservationRepository.find()) {
            CustomerListMytripsService.checkIfTripWasCancelled(reservation);
            if( CustomerListMytripsService.checkIfReservationBelongsToLoggedUser(reservation,loggedUser)){
                    items.add(reservation.toString());
                    myTrips.setItems(items);
           }
        }
    }

    public void goBackToTripPage(javafx.event.ActionEvent backToTripPage) throws Exception{
        Parent backToTripPageInterface = FXMLLoader.load(getClass().getClassLoader().getResource("customer_trip_page.fxml"));
        Stage window = (Stage) ((Node) backToTripPage.getSource()).getScene().getWindow();
        window.setTitle("Trips Page");
        window.setScene(new Scene(backToTripPageInterface, 600, 460));
        window.show();
    }
}
