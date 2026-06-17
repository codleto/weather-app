package org.codleto.weatherapp.exception;

public class InvalidLoginOrPasswordException extends RuntimeException {
    public InvalidLoginOrPasswordException() {
        super("Invalid login or password.");
    }
}
