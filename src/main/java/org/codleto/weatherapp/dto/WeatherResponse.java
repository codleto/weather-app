package org.codleto.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    private String name;
    private MainInfo main;
    private List<WeatherInfo> weather;
}
