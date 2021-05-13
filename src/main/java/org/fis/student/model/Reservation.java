package org.fis.student.model;

public class Reservation {
    private Trip trip;
    private User customer;
    private String mentions;
    private String availability;

    public Reservation(Trip trip, User customer, String mentions){
        this.trip = trip;
        this.customer = customer;
        this.mentions = mentions;
        this.availability = "AVAILABLE";
    }

    public Reservation(){
        this.customer = new User();
        this.trip = new Trip();

    }

    public Trip getTrip(){ return trip;}
    public User getCustomer(){ return customer;}
    public String getMentions(){ return mentions;}
    public void setAvailabilityOnCANCELLED(){ this.availability = "CANCELLED"; }
    public String getDestination(){ return trip.getDestination();}
    public String getDepartureDate(){ return trip.getDeparture_date();}
    public String getReturnDate(){ return trip.getReturn_date();}
    public String getUsername(){ return customer.getUsername();}



    public String toString() {
        if( customer!=null && trip!=null)
            return "Customer: " + customer.getName() + "\n" + "Trip: " + trip.toString() + "\n" + "Availability: " + availability;
        else return "";
    }
}
