package org.codleto.weatherapp.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("User is not authorized.");
    }
}
