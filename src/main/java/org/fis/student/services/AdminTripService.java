package org.fis.student.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis.student.exceptions.*;
import org.fis.student.model.Trip;

import java.util.Objects;

public class AdminTripService {

    private static ObjectRepository<Trip> tripRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("travel-agency-trips.db").toFile())
                .openOrCreate("test", "test");

        tripRepository = database.getRepository(Trip.class);
    }

    public static void addTrip(String destination, String departure_date, String return_date, String description, String price, String number_of_free_spots) throws EmptyTextfieldsException, DestinationAndDateUsedException {
        checkEmptyTextFields(destination,departure_date,return_date,description,price,number_of_free_spots);
        checkDestinationAndDateAlreadyExists(destination,departure_date,return_date);
        tripRepository.insert(new Trip(destination, departure_date, return_date, description, price, number_of_free_spots));
    }

    private static void checkEmptyTextFields(String destination, String departure_date, String return_date, String description, String price, String number_of_free_spots) throws EmptyTextfieldsException{
        if (Objects.equals(destination,""))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(departure_date,""))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(return_date,""))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(description,""))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(price,""))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(number_of_free_spots,""))
            throw new EmptyTextfieldsException();
    }

    public static void checkDestinationAndDateAlreadyExists(String destination, String departure_date, String return_date) throws DestinationAndDateUsedException {
        for (Trip trip : tripRepository.find()) {
            if (Objects.equals(destination, trip.getDestination()) && Objects.equals(departure_date, trip.getDeparture_date()) && Objects.equals(return_date, trip.getReturn_date())) {
                throw new DestinationAndDateUsedException();
            }
        }
    }
}
