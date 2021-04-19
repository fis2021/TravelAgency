package org.fis.student.exceptions;

public class WrongPasswordException extends Exception{
    public WrongPasswordException() {
        super(String.format("Wrong password ! "));
    }

}
