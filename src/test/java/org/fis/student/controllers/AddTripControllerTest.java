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
class AddTripControllerTest {
    
    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        AdminTripService.initDatabase();
        //AdminTripService.addTrip("Dest1","depdate","retdate","abc","230","34");
        UserService.initDatabase();
    }

    @AfterEach
    void tearDown(){

        AdminTripService.getDatabase().close();
        UserService.getDatabase().close();
    }

    @Start
    void start(Stage stage) throws Exception {

        Parent customerInterface = FXMLLoader.load(getClass().getClassLoader().getResource("add_trips.fxml"));
        Scene scene = new Scene(customerInterface);
        stage.setScene(scene);
        stage.show();

    }

    @Test
    @DisplayName("Trip is added successfully")
    void testAddTrip(FxRobot robot) {

        //Empty text fields
        robot.clickOn("#addTrip");
        assertThat(robot.lookup("#add_tripMessage").queryText()).hasText(String.format("You must complete all fields! "));

        //Add a trip
        robot.clickOn("#addDestinationField");
        robot.write("Timisoara");
        robot.clickOn("#addDepartureDateField");
        robot.write("27.05.2021");
        robot.clickOn("#addReturnDateField");
        robot.write("30.05.2021");
        robot.clickOn("#addDescriptionField");
        robot.write("Se va vizita Complexul Studentesc");
        robot.clickOn("#addPriceField");
        robot.write("300 lei");
        robot.clickOn("#addNumberOfFreeSpotsField");
        robot.write("20");
        robot.clickOn("#addTrip");
        assertThat(robot.lookup("#add_tripMessage").queryText()).hasText(String.format("Trip added successfully !"));

        robot.clickOn("#goBackToAdminTripPage");
        robot.clickOn("#goToAddTripAction");

        //Add the same trip
        robot.clickOn("#addDestinationField");
        robot.write("Timisoara");
        robot.clickOn("#addDepartureDateField");
        robot.write("27.05.2021");
        robot.clickOn("#addReturnDateField");
        robot.write("30.05.2021");
        robot.clickOn("#addDescriptionField");
        robot.write("Se va vizita Complexul Studentesc");
        robot.clickOn("#addPriceField");
        robot.write("300 lei");
        robot.clickOn("#addNumberOfFreeSpotsField");
        robot.write("20");
        robot.clickOn("#addTrip");
        assertThat(robot.lookup("#add_tripMessage").queryText()).hasText(String.format("You can't add a new trip with the same destination and date !"));
    }
}