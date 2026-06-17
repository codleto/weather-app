package org.codleto.weatherapp.exception;

public class PasswordsDoNotMatchException extends RuntimeException {
    public PasswordsDoNotMatchException() {
        super("Password don't match");
    }
}
