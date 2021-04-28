package org.fis.student.exceptions;

public class DestinationAndDateUsedException extends Exception{
    public DestinationAndDateUsedException() {
        super(String.format("You can't add a new trip with the same destination and date !"));
    }
}
