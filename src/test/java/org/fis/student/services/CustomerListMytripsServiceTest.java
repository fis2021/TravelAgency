package org.fis.student.services;

import org.apache.commons.io.FileUtils;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.fis.student.model.Reservation;
import org.fis.student.model.Trip;
import org.fis.student.model.User;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerListMytripsServiceTest {

    private User user;
    private Trip trip;
    private Reservation reservation;

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

        UserService.addUser("user","user","Customer","User","adresa","user@email.com","0728","user");
        user = UserService.getAllUsers().get(0);


        AdminTripService.addTrip("Timisoara","26.05.2021","29.05.2021","Se vor vizita obiectivele principale","200 lei","35");
        trip = AdminTripService.getAllTrips().get(0);


        CustomerBookingService.addReservation("Timisoara","Care e orarul excursiei?","26.05.2021","29.05.2021");
        reservation = CustomerBookingService.getAllReservations().get(0);

    }

    @AfterEach
    void tearDown(){
        System.out.println("After class");
        CustomerBookingService.getDatabase().close();
        AdminTripService.getDatabase().close();
        UserService.getDatabase().close();
    }

    @Test
    void testCheckIfTripWasCancelled(){

        CustomerListMytripsService.checkIfTripWasCancelled(reservation);
        assertThat(reservation.getAvailability()).isEqualTo("AVAILABLE");

        AdminTripService.getTripRepository().remove(ObjectFilters.ALL);

        CustomerListMytripsService.checkIfTripWasCancelled(reservation);
        assertThat(reservation.getAvailability()).isEqualTo("CANCELLED");
    }

}