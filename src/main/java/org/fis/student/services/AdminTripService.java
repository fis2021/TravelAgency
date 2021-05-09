package org.fis.student.services;

import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectFilter;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis.student.exceptions.*;
import org.fis.student.model.Trip;

import java.util.Objects;

public class AdminTripService {

    private static ObjectRepository<Trip> tripRepository;

    public static ObjectRepository<Trip> getTripRepository() {
        return tripRepository;
    }

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

    public static void editTrip(String destination1, String departure_date, String return_date, String description, String price, String number_of_free_spots, String departure_date1, String return_date1) throws EmptyTextfieldsException, DestinationAndDateExistsException{
        checkEmptyTextFieldsForEdit(destination1,departure_date,return_date);
        checkTripExists(destination1,departure_date,return_date);

        Trip trip_aux = new Trip();

        for (Trip trip : tripRepository.find()) {
            if (Objects.equals(destination1, trip.getDestination()) && Objects.equals(departure_date, trip.getDeparture_date()) && Objects.equals(return_date, trip.getReturn_date())) {
                trip_aux = trip;
            }
        }

        if (!Objects.equals(departure_date1,"")){
            trip_aux.setDeparture_date(departure_date1);
        }
        if (!Objects.equals(return_date1,"")){
            trip_aux.setReturn_date(return_date1);
        }
        if (!Objects.equals(description,"")){
            trip_aux.setDescription(description);
        }
        if (!Objects.equals(price,"")){
            trip_aux.setPrice(price);
        }
        if (!Objects.equals(number_of_free_spots,"")){
            trip_aux.setNumber_of_free_spots(number_of_free_spots);
        }

        tripRepository.update(eq("destination",destination1), trip_aux);
    }

    public static void deleteTrip(String destination, String departure_date, String return_date) throws EmptyTextfieldsException, DestinationAndDateExistsException{
        checkEmptyTextFieldsForEdit(destination,departure_date,return_date);
        checkTripExists(destination,departure_date,return_date);

        tripRepository.remove(eq("destination",destination));
    }

    private static void checkEmptyTextFieldsForEdit(String destination, String departure_date, String return_date) throws EmptyTextfieldsException{
        if (Objects.equals(destination,""))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(departure_date,""))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(return_date,""))
            throw new EmptyTextfieldsException();
    }

    private static void checkTripExists(String destination, String departure_date, String return_date) throws DestinationAndDateExistsException {

        int ok = 0;

        for (Trip trip : tripRepository.find()) {
            if (Objects.equals(destination, trip.getDestination()) && Objects.equals(departure_date, trip.getDeparture_date()) && Objects.equals(return_date, trip.getReturn_date())) {
                ok = 1;
            }
        }

        if (ok == 0) throw new DestinationAndDateExistsException();

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
