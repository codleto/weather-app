package org.codleto.weatherapp.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("Account with this username already exists.");
    }
}
