package org.fis.student.services;

import org.apache.commons.io.FileUtils;;
import org.fis.student.exceptions.*;
import org.fis.student.model.Reservation;
import org.fis.student.model.Trip;
import org.fis.student.model.User;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AdminTripServiceTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        CustomerBookingService.initDatabase();
        AdminTripService.initDatabase();
        UserService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        System.out.println("After class");
        CustomerBookingService.getDatabase().close();
        AdminTripService.getDatabase().close();
        UserService.getDatabase().close();
    }
    
    @Test
    @DisplayName("Database for trips is initialized, and there are no trips")
    void testDatabaseIsInitializedAndNoTripIsPersisted() {
        assertThat(AdminTripService.getAllTrips()).isNotNull();
        assertThat(AdminTripService.getAllTrips()).isEmpty();
    }

    @Test
    @DisplayName("Trip is successfully added to the database")
    void testTripIsAddedToDatabase() throws Exception {

        String loggedUser = "travel_agency";

        //Add a Travel Agency
        UserService.addUser("travel_agency","travel_agency","Travel Agency","Travel Agency","Adresa","travel_agency@emai.com","0712","travel_agency");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User travel_agency = UserService.getAllUsers().get(0);

        //Add a trip
        AdminTripService.addTrip("Timisoara","26.05.2021","29.05.2021","Se vor vizita obiectivele principale","200 lei","35");
        assertThat(AdminTripService.getAllTrips()).isNotNull();
        assertThat(AdminTripService.getAllTrips()).size().isEqualTo(1);
        Trip trip = AdminTripService.getAllTrips().get(0);
    }

    @Test
    @DisplayName("When adding a trip, destination must be completed")
    void testCheckDestinationFieldCompleted() {
        assertThrows(EmptyTextfieldsException.class, () -> {
            AdminTripService.checkEmptyTextFields("","1","1","1","1","1");
        });
    }

    @Test
    @DisplayName("When adding a trip, departure date must be completed")
    void testCheckDepartureDateFieldCompleted() {
        assertThrows(EmptyTextfieldsException.class, () -> {
            AdminTripService.checkEmptyTextFields("1","","1","1","1","1");
        });
    }

    @Test
    @DisplayName("When adding a trip, return date must be completed")
    void testCheckReturnDateFieldCompleted() {
        assertThrows(EmptyTextfieldsException.class, () -> {
            AdminTripService.checkEmptyTextFields("1","1","","1","1","1");
        });
    }

    @Test
    @DisplayName("When adding a trip, description must be completed")
    void testCheckDescriptionFieldCompleted() {
        assertThrows(EmptyTextfieldsException.class, () -> {
            AdminTripService.checkEmptyTextFields("1","1","1","","1","1");
        });
    }

    @Test
    @DisplayName("When adding a trip, price must be completed")
    void testCheckPriceFieldCompleted() {
        assertThrows(EmptyTextfieldsException.class, () -> {
            AdminTripService.checkEmptyTextFields("1","1","1","1","","1");
        });
    }

    @Test
    @DisplayName("When adding a trip, number of free spots must be completed")
    void testCheckNumberOfFreeSpotsCompleted() {
        assertThrows(EmptyTextfieldsException.class, () -> {
            AdminTripService.checkEmptyTextFields("1","1","1","1","1","");
        });
    }

    @Test
    @DisplayName("Trip must have unique destination and dates")
    void testDestinationAndDateExist() {
        assertThrows(DestinationAndDateUsedException.class, () -> {
            AdminTripService.addTrip("Timisoara","26.05.2021","29.05.2021","Se vor vizita obiectivele principale","200 lei","35");
            AdminTripService.addTrip("Timisoara","26.05.2021","29.05.2021","Se vor vizita obiectivele principale","200 lei","35");
        });
    }

    @Test
    @DisplayName("Trip is successfully edited")
    void testTripIsEdited() throws Exception{

        String loggedUser = "travel_agency";

        //Add a Travel Agency
        UserService.addUser("travel_agency","travel_agency","Travel Agency","Travel Agency","Adresa","travel_agency@emai.com","0712","travel_agency");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User travel_agency = UserService.getAllUsers().get(0);

        //Add a trip
        AdminTripService.addTrip("Timisoara","26.05.2021","29.05.2021","Se vor vizita obiectivele principale","200 lei","35");
        assertThat(AdminTripService.getAllTrips()).isNotNull();
        assertThat(AdminTripService.getAllTrips()).size().isEqualTo(1);
        Trip trip = AdminTripService.getAllTrips().get(0);

        //Edit the trip
        AdminTripService.editTrip("Timisoara","26.05.2021","29.05.2021","Se va vizita Complexul Studentesc","300 lei","20","27.05.2021","30.05.2021");
        assertThat(AdminTripService.getAllTrips()).isNotNull();
        assertThat(AdminTripService.getAllTrips()).size().isEqualTo(1);

        Trip trip_edited = AdminTripService.getAllTrips().get(0);

        assertThat(trip_edited).isNotNull();
        assertThat(trip_edited.getDeparture_date()).isEqualTo("27.05.2021");
        assertThat(trip_edited.getReturn_date()).isEqualTo("30.05.2021");
        assertThat(trip_edited.getDescription()).isEqualTo("Se va vizita Complexul Studentesc");
        assertThat(trip_edited.getPrice()).isEqualTo("300 lei");
        assertThat(trip_edited.getNumber_of_free_spots()).isEqualTo("20");
    }

    @Test
    @DisplayName("When editing a trip, destination must be completed")
    void testCheckDestinationFieldForEditCompleted() {
        assertThrows(EmptyTextfieldsException.class, () -> {
            AdminTripService.checkEmptyTextFieldsForEdit("","1","1");
        });
    }

    @Test
    @DisplayName("When editing a trip, departure date must be completed")
    void testCheckDepartureDateFieldForEditCompleted() {
        assertThrows(EmptyTextfieldsException.class, () -> {
            AdminTripService.checkEmptyTextFieldsForEdit("1","","1");
        });
    }

    @Test
    @DisplayName("When editing a trip, destination must be completed")
    void testCheckReturnDateFieldForEditCompleted() {
        assertThrows(EmptyTextfieldsException.class, () -> {
            AdminTripService.checkEmptyTextFieldsForEdit("1","1","");
        });
    }

    @Test
    @DisplayName("When editing a trip, the trip must exist")
    void testCheckTripExists() {
        assertThrows(DestinationAndDateExistsException.class, () -> {
            AdminTripService.checkTripExists("1","1","1");
        });
    }

    @Test
    @DisplayName("Trip is successfully deleted")
    void testTripIsDeleted() throws Exception{

        String loggedUser = "travel_agency";

        //Add a Travel Agency
        UserService.addUser("travel_agency","travel_agency","Travel Agency","Travel Agency","Adresa","travel_agency@emai.com","0712","travel_agency");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User travel_agency = UserService.getAllUsers().get(0);

        //Add a trip
        AdminTripService.addTrip("Timisoara","26.05.2021","29.05.2021","Se vor vizita obiectivele principale","200 lei","35");
        assertThat(AdminTripService.getAllTrips()).isNotNull();
        assertThat(AdminTripService.getAllTrips()).size().isEqualTo(1);
        Trip trip = AdminTripService.getAllTrips().get(0);

        //Delete the trip
        AdminTripService.deleteTrip("Timisoara","26.05.2021","29.05.2021");
        assertThat(AdminTripService.getAllTrips()).isNotNull();
        assertThat(AdminTripService.getAllTrips()).size().isEqualTo(0);
    }
}