package org.fis.student.exceptions;

public class EmptyTextfieldsException extends Exception{
    public EmptyTextfieldsException() {
        super(String.format("You must complete all fields! "));
    }
}
