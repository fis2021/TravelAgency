package org.fis.student.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis.student.controllers.LoginController;
import org.fis.student.exceptions.DestinationAndDateUsedException;
import org.fis.student.exceptions.EmptyTextfieldsException;
import org.fis.student.exceptions.TripDoesNotExistException;
import org.fis.student.model.Reservation;
import org.fis.student.model.Trip;
import org.fis.student.model.User;

import java.util.List;
import java.util.Objects;

public class CustomerBookingService {
    private static ObjectRepository<Reservation> bookingRepository;
    private static Nitrite database;

    private static ObjectRepository<Trip> tripRepository = AdminTripService.getTripRepository();

    private static ObjectRepository<User> userRepository = UserService.getUserRepository();

    public static ObjectRepository<Reservation> getBookingRepository() {
        return bookingRepository;
    }

    private static String loggedUser;

    public static void initDatabase() {
        database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("travel-agency-reservations.db").toFile())
                .openOrCreate("test", "test");

        bookingRepository = database.getRepository(Reservation.class);
    }
    public static void addReservation(String destination, String mentions, String departure_date, String return_date) throws EmptyTextfieldsException, TripDoesNotExistException{
        checkEmptyTextfields(destination,departure_date,return_date);
        tripRepository = AdminTripService.getTripRepository();
        userRepository = UserService.getUserRepository();
        checkIfTripExists(destination,departure_date,return_date);
        loggedUser = LoginController.getLoggedUser();
        User customer = new User();
        Trip bookedTrip = new Trip();
        for (User user : userRepository.find()) {
            if (Objects.equals(loggedUser, user.getUsername())){
                customer = user;
            }
        }
        for (Trip trip : tripRepository.find()) {
            if (Objects.equals(destination, trip.getDestination()) && Objects.equals(departure_date, trip.getDeparture_date()) && Objects.equals(return_date, trip.getReturn_date())) {
                bookedTrip = trip;
            }
        }
        bookingRepository.insert(new Reservation(bookedTrip,customer,mentions));
    }

    public static void checkIfTripExists(String destination, String departure_date, String return_date) throws TripDoesNotExistException{
        int ok = 0;
        tripRepository = AdminTripService.getTripRepository();
        for (Trip trip : tripRepository.find()) {
            if (Objects.equals(destination, trip.getDestination()) && Objects.equals(departure_date, trip.getDeparture_date()) && Objects.equals(return_date, trip.getReturn_date())) {
                ok = 1;
            }
        }
        if( ok == 0 )
            throw new TripDoesNotExistException();
    }

    public static void checkEmptyTextfields(String destination, String departure_date, String return_date) throws EmptyTextfieldsException {
        if( Objects.equals(destination,""))
            throw new EmptyTextfieldsException();
        else if( Objects.equals(departure_date,""))
            throw new EmptyTextfieldsException();
        else if( Objects.equals(return_date,""))
            throw new EmptyTextfieldsException();
    }

    public static List<Reservation> getAllReservations() {
        return bookingRepository.find().toList();
    }

    public static Nitrite getDatabase() {
        return database;
    }
}
