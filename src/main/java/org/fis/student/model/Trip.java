package org.fis.student.model;

public class Trip {

    private String destination;
    private String departure_date;
    private String return_date;
    private String description;
    private String price;
    private String number_of_free_spots;

    public Trip(String destination, String departure_date, String return_date, String description, String price, String number_of_free_spots){
        this.destination = destination;
        this.departure_date = departure_date;
        this.return_date = return_date;
        this.description = description;
        this.price = price;
        this.number_of_free_spots = number_of_free_spots;
    }

    public Trip(){

    }

    public String getDestination() {return destination; }

    public void setDestination(String destination) {this.destination = destination; }

    public String getDeparture_date() {return departure_date; }

    public void setDeparture_date(String departure_date) {this.departure_date = departure_date; }

    public String getReturn_date() {return return_date; }

    public void setReturn_date(String return_date) {this.return_date = return_date; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getPrice () {return price; }

    public void setPrice(String price) {this.price = price; }

    public String getNumber_of_free_spots() {return number_of_free_spots; }

    public void setNumber_of_free_spots(String number_of_free_spots) {this.number_of_free_spots = number_of_free_spots; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trip trip = (Trip) o;

        if (destination != null ? !destination.equals(trip.destination) : trip.destination != null) return false;
        if (departure_date != null ? !departure_date.equals(trip.departure_date) : trip.departure_date != null) return false;
        if (return_date != null ? !return_date.equals(trip.return_date) : trip.return_date != null) return false;
        if (description != null ? !description.equals(trip.description) : trip.description != null) return false;
        if (price != null ? !price.equals(trip.price) : trip.price != null) return false;
        if (number_of_free_spots != null ? !number_of_free_spots.equals(trip.number_of_free_spots) : trip.number_of_free_spots != null) return false;
        return true;
    }
}
