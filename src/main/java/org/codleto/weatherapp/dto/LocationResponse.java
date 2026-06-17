package org.codleto.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationResponse {

    private String name;
    private double lat;
    private double lon;
    private String country;
    private String state;
}
