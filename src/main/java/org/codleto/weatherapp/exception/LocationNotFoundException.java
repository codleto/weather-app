package org.codleto.weatherapp.exception;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException() {
        super("Location not found.");
    }
}
