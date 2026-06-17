package org.codleto.weatherapp.client;

import org.codleto.weatherapp.dto.LocationResponse;
import org.codleto.weatherapp.dto.WeatherResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class WeatherApiClientTest {

    @Autowired
    private WeatherApiClient weatherApiClient;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void findLocation_shouldReturnLocations() {

        LocationResponse location = new LocationResponse();

        LocationResponse[] response = {location};

        when(restTemplate.getForObject(
                anyString(),
                eq(LocationResponse[].class)
        )).thenReturn(response);

        LocationResponse[] result = weatherApiClient.findLocation("Moscow");

        assertNotNull(result);
        assertEquals(1, result.length);
    }

    @Test
    void getWeather_shouldReturnWeather() {

        WeatherResponse weatherResponse = new WeatherResponse();

        when(restTemplate.getForObject(
                anyString(),
                eq(WeatherResponse.class)
        )).thenReturn(weatherResponse);

        WeatherResponse result = weatherApiClient.getWeather(55.75, 37.61);

        assertNotNull(result);
    }
}
