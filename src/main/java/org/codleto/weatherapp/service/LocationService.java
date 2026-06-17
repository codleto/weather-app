package org.codleto.weatherapp.service;

import lombok.RequiredArgsConstructor;
import org.codleto.weatherapp.client.WeatherApiClient;
import org.codleto.weatherapp.dto.SavedLocationWeatherDto;
import org.codleto.weatherapp.dto.WeatherResponse;
import org.codleto.weatherapp.entity.Location;
import org.codleto.weatherapp.entity.User;
import org.codleto.weatherapp.exception.LocationNotFoundException;
import org.codleto.weatherapp.repository.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final WeatherApiClient client;

    @Transactional(readOnly = true)
    public List<SavedLocationWeatherDto> getSavedLocationsWeather(User user) {

        List<Location> locations = locationRepository.findByUser(user);

        List<SavedLocationWeatherDto> result = new ArrayList<>();

        for (Location location : locations) {

            double lat = location.getLatitude().doubleValue();
            double lon = location.getLongitude().doubleValue();

            SavedLocationWeatherDto dto = new SavedLocationWeatherDto(location.getId(), client.getWeather(lat, lon));

            result.add(dto);
        }

        return result;
    }

    @Transactional
    public void addLocation(String name, User user, BigDecimal latitude, BigDecimal longitude) {

        Location location = new Location(name, user, latitude, longitude);

        locationRepository.save(location);
    }

    @Transactional
    public void deleteLocation(Long id, User user) {

        Location location = locationRepository.findByIdAndUser(id, user);

        if (location == null) {
            throw new LocationNotFoundException();
        }

        locationRepository.delete(location);
    }
}
