package de.hbrs.service;

import de.hbrs.model.Coordinates;
import de.hbrs.model.Forecast;
import de.hbrs.model.Location;
import de.hbrs.model.Temperature;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DataAcquisitionService {

    // Statics
    // example with ngrok: 'https://fde1-2a02-908-620-85c0-500-89fb-61c3-8e31.eu.ngrok.io' -> 'http://localhost:8080'
    private static final String DATA_ACQUISITION_API = "http://localhost:5098";
    private static final String LOCATION_ENDPOINT    = "/Locations";
    private static final String WEATHER_ENDPOINT     = "/WeatherForecast";

    // Endpoint Gates
    // WeatherForecast endpoint gate
    private List<Forecast> queryWeatherEndpoint(double longitude, double latitude) {
        String url =
              DATA_ACQUISITION_API
            + WEATHER_ENDPOINT
            + "?longitude=" + longitude
            + "&latitude=" + latitude;

        RestTemplate restTemplate = new RestTemplate();

        Forecast[] forcasts = restTemplate.getForObject(url, Forecast[].class);

        return List.of(forcasts);
    }

    // Location endpoint gate
    private List<Location> queryLocationEndpoint() {
        String url =
              DATA_ACQUISITION_API
            + LOCATION_ENDPOINT
            + "/all";

        RestTemplate restTemplate = new RestTemplate();

        Location[] forcasts = restTemplate.getForObject(url, Location[].class);

        return List.of(forcasts);
    }

    // Get unfiltered forecasts by coordinates
    public List<Forecast> getForecasts(Coordinates coordinates) {
        return queryWeatherEndpoint(coordinates.longitude(), coordinates.latitude());
    }

    // Get filtered forecast by coordinates and time
    public Forecast getForecast(Coordinates coordinates, int timeOffset) {
        return this.getForecasts(coordinates).get(timeOffset);
    }

    // Get unfiltered temperatures by coordinates
    public List<Temperature> getTemperatures(Coordinates coordinates) {
        return this.getForecasts(coordinates).stream()
            .map(Forecast::temperature)
            .map(Temperature::new)
            .collect(Collectors.toList());
    }

    // Get filtered temperature by coordinates and time
    public Temperature getTemperature(Coordinates coordinates, int timeOffset) {
        return this.getTemperatures(coordinates).get(timeOffset);
    }

    // Translate a locationCode into a coordinates
    public Optional<Coordinates> translateLocationCodeToCoordinates(String locationCode) {
        List<Location> locations = this.queryLocationEndpoint();

        return locations.stream()
            .filter(
                loc -> loc.getComparableAttributes().stream()
                    .anyMatch(locAtr -> locAtr.contains(locationCode))
            )
            .map(Coordinates::new)
            .findAny();
    }

}
