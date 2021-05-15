package org.fis.student.services;

import org.apache.commons.io.FileUtils;
import org.fis.student.controllers.LoginController;
import org.fis.student.exceptions.*;
import org.fis.student.model.Reservation;
import org.fis.student.model.Trip;
import org.fis.student.model.User;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerBookingServiceTest {

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
    void tearDown(){
        System.out.println("After class");
        CustomerBookingService.getDatabase().close();
        AdminTripService.getDatabase().close();
        UserService.getDatabase().close();
    }

    @Test
    @DisplayName("Database for reservations is initialized, and there are no reservations")
    void testDatabaseIsInitializedAndNoReservationIsPersisted() {
        assertThat(CustomerBookingService.getAllReservations()).isNotNull();
        assertThat(CustomerBookingService.getAllReservations()).isEmpty();
    }

    @Test
    @DisplayName("Database for trips is initialized, and there are no trips")
    void testDatabaseIsInitializedAndNoTripIsPersisted() {
        assertThat(AdminTripService.getAllTrips()).isNotNull();
        assertThat(AdminTripService.getAllTrips()).isEmpty();
    }

    @Test
    @DisplayName("Reservation is successfully added to database")
    void testReservationIsAddedToDatabase() throws Exception {

        String  loggedUser = "user";
        //Add a user to book the trip
        UserService.addUser("user","user","Customer","User","adresa","user@email.com","0728","user");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user = UserService.getAllUsers().get(0);

        //Add a trip to be booked
        AdminTripService.addTrip("Timisoara","26.05.2021","29.05.2021","Se vor vizita obiectivele principale","200 lei","35");
        assertThat(AdminTripService.getAllTrips()).isNotNull();
        assertThat(AdminTripService.getAllTrips()).size().isEqualTo(1);
        Trip trip = AdminTripService.getAllTrips().get(0);

        //then, try to book the trip
        CustomerBookingService.addReservation("Timisoara","Care e orarul excursiei?","26.05.2021","29.05.2021");
        assertThat(CustomerBookingService.getAllReservations()).isNotNull();
        assertThat(CustomerBookingService.getAllReservations()).size().isEqualTo(1);

        Reservation reservation = CustomerBookingService.getAllReservations().get(0);
        assertThat(reservation).isNotNull();
        assertThat(reservation.getDestination()).isEqualTo("Timisoara");
        assertThat(reservation.getMentions()).isEqualTo("Care e orarul excursiei?");
        assertThat(reservation.getDepartureDate()).isEqualTo("26.05.2021");
        assertThat(reservation.getReturnDate()).isEqualTo("29.05.2021");


        //try book 2 reservations
        CustomerBookingService.addReservation("Timisoara","Care e orarul excursiei?","26.05.2021","29.05.2021");
        assertThat(CustomerBookingService.getAllReservations()).isNotNull();
        assertThat(CustomerBookingService.getAllReservations()).size().isEqualTo(2);
    }

    @Test
    @DisplayName("When making a reservation, destination must be completed")
    void testCheckDestinationFieldCompleted(){
        assertThrows(EmptyTextfieldsException.class, () -> {
            CustomerBookingService.checkEmptyTextfields("","1","1");
        });
    }

    @Test
    @DisplayName("When making a reservation, departure date must be completed")
    void testCheckDepDateFieldCompleted(){
        assertThrows(EmptyTextfieldsException.class, () -> {
            CustomerBookingService.checkEmptyTextfields("1","","1");
        });
    }

    @Test
    @DisplayName("When making a reservation, return date must be completed")
    void testCheckRetDateFieldCompleted(){
        assertThrows(EmptyTextfieldsException.class, () -> {
            CustomerBookingService.checkEmptyTextfields("1","1","");
        });
    }

    @Test
    @DisplayName("When making a reservation, the trip must exist")
    void testCheckIfReservedTripExists(){
        assertThrows(TripDoesNotExistException.class, () -> {
            CustomerBookingService.checkIfTripExists("","","");
        });
    }

}