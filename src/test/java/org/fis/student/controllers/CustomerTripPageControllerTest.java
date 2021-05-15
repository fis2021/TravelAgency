package org.fis.student.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis.student.model.User;
import org.fis.student.services.AdminTripService;
import org.fis.student.services.CustomerBookingService;
import org.fis.student.services.FileSystemService;
import org.fis.student.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class CustomerTripPageControllerTest {

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
    }

    @AfterEach
    void tearDown(){

        AdminTripService.getDatabase().close();
        UserService.getDatabase().close();
        CustomerBookingService.getDatabase().close();
    }

    @Start
    void start(Stage stage) throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        AdminTripService.getDatabase().close();
        UserService.getDatabase().close();
        CustomerBookingService.getDatabase().close();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        AdminTripService.initDatabase();
        AdminTripService.addTrip("Dest1","depdate","retdate","abc","230","34");
        AdminTripService.addTrip("Dest2","depdate","retdate","abc","230","34");
        AdminTripService.addTrip("Dest3","depdate","retdate","abc","230","34");

        Parent customerInterface = FXMLLoader.load(getClass().getClassLoader().getResource("customer_trip_page.fxml"));
        Scene scene = new Scene(customerInterface);
        stage.setScene(scene);
        stage.show();

        AdminTripService.getDatabase().close();

    }

    @Test
    void test(FxRobot robot) throws Exception{
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        AdminTripService.initDatabase();
        AdminTripService.addTrip("Dest1","depdate","retdate","abc","230","34");
        AdminTripService.addTrip("Dest2","depdate","retdate","abc","230","34");
        AdminTripService.addTrip("Dest3","depdate","retdate","abc","230","34");

        CustomerBookingService.initDatabase();
        UserService.initDatabase();
        CustomerBookingService.addReservation("Dest1","","depdate","retdate");
        CustomerBookingService.addReservation("Dest2","","depdate","retdate");
        CustomerBookingService.addReservation("Dest3","","depdate","retdate");

        UserService.addUser("user","user","Customer","User","adresa","user@email.com","0728","user");

        robot.clickOn("#goToBookTripAction");
        robot.clickOn("#goBackToCustomerTripPage");

        robot.clickOn("#goToListTripsAction");
        robot.clickOn("#goBackToCustomerTripPage");

    }
}