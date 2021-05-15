package org.fis.student.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis.student.services.AdminTripService;
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
class EditTripControllerTest {

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        AdminTripService.initDatabase();
        AdminTripService.addTrip("Dest1","depdate","retdate","abc","230","34");
    }

    @AfterEach
    void tearDown(){
        AdminTripService.getDatabase().close();
    }

    @Start
    void start(Stage stage) throws Exception {

        Parent customerInterface = FXMLLoader.load(getClass().getClassLoader().getResource("edit_trips.fxml"));
        Scene scene = new Scene(customerInterface);
        stage.setScene(scene);
        stage.show();

    }

    @Test
    @DisplayName("Trip is edited successfully")
    void testEditTrip(FxRobot robot) {

        //Empty text fields
        robot.clickOn("#editTrip");
        assertThat(robot.lookup("#edit_tripMessage").queryText()).hasText(String.format("You must complete all fields! "));

        //Edit the trip
        robot.clickOn("#editDestinationField1");
        robot.write("Dest1");
        robot.clickOn("#editDepartureDateField");
        robot.write("depdate");
        robot.clickOn("#editReturnDateField1");
        robot.write("retdate");
        robot.clickOn("#editPriceField");
        robot.write("300");
        robot.clickOn("#editTrip");
        assertThat(robot.lookup("#edit_tripMessage").queryText()).hasText(String.format("Trip edited successfully!"));
    }
}