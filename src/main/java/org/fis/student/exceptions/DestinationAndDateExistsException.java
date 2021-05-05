package org.fis.student.exceptions;

import java.util.concurrent.ExecutionException;

public class DestinationAndDateExistsException extends ExecutionException {
    public DestinationAndDateExistsException() {
        super(String.format("A trip with the given information doesn't exist. Reenter the information!"));
    }
}
