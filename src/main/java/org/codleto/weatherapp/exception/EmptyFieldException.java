package org.codleto.weatherapp.exception;

public class EmptyFieldException extends RuntimeException {
    public EmptyFieldException() {
        super("All fields must be filled in.");
    }
}
