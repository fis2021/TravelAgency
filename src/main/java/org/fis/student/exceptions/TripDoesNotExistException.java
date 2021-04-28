package org.fis.student.exceptions;

public class TripDoesNotExistException extends Exception{
    public TripDoesNotExistException() {
        super(String.format("The trip having the information you entered doesn't exist ! "));
    }
}
