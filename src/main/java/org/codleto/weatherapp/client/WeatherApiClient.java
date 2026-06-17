package org.codleto.weatherapp.client;

import lombok.RequiredArgsConstructor;
import org.codleto.weatherapp.dto.LocationResponse;
import org.codleto.weatherapp.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class WeatherApiClient {

    private final RestTemplate restTemplate;

    @Value("${weather.geo.url}")
    private String geoUrl;

    @Value("${weather.weather.url}")
    private String weatherUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    public LocationResponse[] findLocation(String city) {
        String url = geoUrl
                + "?q="
                + city
                + "&limit=5"
                + "&appid="
                + apiKey;

        return restTemplate.getForObject(url, LocationResponse[].class);
    }

    public WeatherResponse getWeather(double lat, double lon) {
        String url = weatherUrl
                + "?lat="
                + lat
                + "&lon="
                + lon
                + "&units=metric"
                + "&appid="
                + apiKey;
        return restTemplate.getForObject(url, WeatherResponse.class);
    }
}
