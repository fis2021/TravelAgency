package org.fis.student.exceptions;

public class EmailAlreadyUsedException extends Exception{
    public EmailAlreadyUsedException() {
        super(String.format("You can't use the same email for more than one account ! "));
    }
}
