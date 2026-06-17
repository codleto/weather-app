package org.codleto.weatherapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.codleto.weatherapp.client.WeatherApiClient;
import org.codleto.weatherapp.dto.LocationResponse;
import org.codleto.weatherapp.dto.SavedLocationWeatherDto;
import org.codleto.weatherapp.dto.WeatherResponse;
import org.codleto.weatherapp.entity.User;
import org.codleto.weatherapp.exception.LocationNotFoundException;
import org.codleto.weatherapp.exception.UnauthorizedException;
import org.codleto.weatherapp.service.CurrentUserService;
import org.codleto.weatherapp.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WeatherController {

    private final CurrentUserService currentUserService;
    private final LocationService locationService;
    private final WeatherApiClient weatherApiClient;

    @GetMapping("/index")
    public String index(HttpServletRequest request, Model model) {

        try {
            User user = currentUserService.getCurrentUser(request);

            List<SavedLocationWeatherDto> savedLocationsWeather = locationService.getSavedLocationsWeather(user);

            model.addAttribute("savedLocationsWeather", savedLocationsWeather);

            return "index";

        } catch (UnauthorizedException e) {
            return "redirect:/sign-in";
        }
    }

    @GetMapping("/locations")
    public String findLocations(@RequestParam String city, Model model) {

        LocationResponse[] locations = weatherApiClient.findLocation(city);

        model.addAttribute("locations", locations);
        model.addAttribute("city", city);

        return "search-results";
    }

    @PostMapping("/locations")
    public String addLocation(@RequestParam String name,
                              @RequestParam BigDecimal latitude,
                              @RequestParam BigDecimal longitude,
                              HttpServletRequest request) {

        User user = currentUserService.getCurrentUser(request);

        locationService.addLocation(name, user, latitude, longitude);

        return "redirect:/index";
    }

    @PostMapping("/locations/delete")
    public String deleteLocation(@RequestParam Long id,
                                 HttpServletRequest request,
                                 Model model) {

        try {
            User user = currentUserService.getCurrentUser(request);

            locationService.deleteLocation(id, user);

            return "redirect:/index";

        } catch (UnauthorizedException e) {
            return "redirect:/sign-in";

        } catch (LocationNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
    }
}
