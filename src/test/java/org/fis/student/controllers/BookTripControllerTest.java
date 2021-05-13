package org.fis.student.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis.student.services.AdminTripService;
import org.fis.student.services.CustomerBookingService;
import org.fis.student.services.FileSystemService;
import org.fis.student.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class BookTripControllerTest {

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        AdminTripService.initDatabase();
        AdminTripService.addTrip("Dest1","depdate","retdate","abc","230","34");
        CustomerBookingService.initDatabase();
        UserService.initDatabase();
    }

    @AfterEach
    void tearDown(){

        AdminTripService.getDatabase().close();
        //UserService.getDatabase().close();
        CustomerBookingService.getDatabase().close();
    }

    @Start
    void start(Stage stage) throws Exception {

        Parent customerInterface = FXMLLoader.load(getClass().getClassLoader().getResource("book_trip.fxml"));
        Scene scene = new Scene(customerInterface);
        stage.setScene(scene);
        stage.show();

    }

    @Test
    @DisplayName("Reservation is done successfully")
    void testBookTripFeature(FxRobot robot){

        //empty text fields are not allowed
        robot.clickOn("#BookTripButton");
        assertThat(robot.lookup("#bookTripMessage").queryText()).hasText(String.format("You must complete all fields! "));

        //trip must exist
        robot.clickOn("#destinationFieldBooking");
        robot.write("Gresit");
        robot.clickOn("#departureDateFieldBooking");
        robot.write("2");
        robot.clickOn("#returnDateFieldBooking");
        robot.write("2");
        robot.clickOn("#mentionsFieldBooking");
        robot.write("a");

        robot.clickOn("#BookTripButton");
        assertThat(robot.lookup("#bookTripMessage").queryText()).hasText("The trip having the information you entered doesn't exist ! ");

        robot.clickOn("#goBackToCustomerTripPage");
        robot.clickOn("#goToBookTripAction");

        //correct reservation
        robot.clickOn("#destinationFieldBooking");
        robot.write("Dest1");
        robot.clickOn("#departureDateFieldBooking");
        robot.write("depdate");
        robot.clickOn("#returnDateFieldBooking");
        robot.write("retdate");
        robot.clickOn("#mentionsFieldBooking");
        robot.write("a");

        robot.clickOn("#BookTripButton");
        assertThat(robot.lookup("#bookTripMessage").queryText()).hasText("Trip booked successfully!");




    }

}