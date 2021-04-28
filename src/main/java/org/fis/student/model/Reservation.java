package org.fis.student.model;

public class Reservation {
    private Trip trip;
    private User customer;
    private String mentions;

    public Reservation(Trip trip, User customer, String mentions){
        this.trip = trip;
        this.customer = customer;
        this.mentions = mentions;
    }
}
