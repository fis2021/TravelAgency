package org.fis.student.services;

import org.dizitart.no2.objects.ObjectRepository;
import org.fis.student.model.Reservation;
import org.fis.student.model.Trip;

import java.util.Objects;

import static org.dizitart.no2.objects.filters.ObjectFilters.and;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;


public class CustomerListMytripsService {

    private static ObjectRepository<Trip> tripRepository = AdminTripService.getTripRepository();
    private static ObjectRepository<Reservation> reservationRepository = CustomerBookingService.getBookingRepository();

    public static void checkIfTripWasCancelled(Reservation reservation) {
        int ok = 0;
        for (Trip trip : tripRepository.find()) {
            if( Objects.equals(trip,reservation.getTrip())) {
                ok = 1;
                break;
            }
        }

        if( ok == 0 ) {
            reservation.setAvailabilityOnCANCELLED();
            reservationRepository.update((and(eq("destination",reservation.getTrip().getDestination()), eq("departure_date",reservation.getTrip().getDeparture_date()), eq("username",reservation.getCustomer().getUsername()))),reservation);

        }
    }


    public static boolean checkIfReservationBelongsToLoggedUser(Reservation reservation, String loggedUser) {
        if(Objects.equals(reservation.getCustomer().getUsername(),loggedUser))
            return true;
        else return false;
    }
}
