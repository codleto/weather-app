package org.codleto.weatherapp.dto;

public record SavedLocationWeatherDto(Long locationId, WeatherResponse weather) {
}
