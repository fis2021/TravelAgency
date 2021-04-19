package org.fis.student.exceptions;

public class WrongRoleException extends Exception{
    public WrongRoleException() {
        super(String.format("You selected the incorrect role! "));
    }
}
