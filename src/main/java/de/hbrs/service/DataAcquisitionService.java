package de.hbrs.service;

import de.hbrs.model.Coordinate;
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
    // example with ngrok: 'https://fde1-2a02-908-620-85c0-500-89fb-61c3-8e31.eu.ngrok.io' -> http://localhost:8080
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

    // Get unfiltered forecasts by coordinate
    public List<Forecast> getForecasts(Coordinate coordinate) {
        return queryWeatherEndpoint(coordinate.getLongitude(), coordinate.getLatitude());
    }

    // Get filtered forecast by coordinate and time
    public Forecast getForecast(Coordinate coordinate, int timeOffset) {
        return this.getForecasts(coordinate).get(timeOffset);
    }

    // Get unfiltered temperatures by coordinate
    public List<Temperature> getTemperatures(Coordinate coordinate) {
        return this.getForecasts(coordinate).stream()
            .map(Forecast::getTemperature)
            .map(Temperature::new)
            .collect(Collectors.toList());
    }

    // Get filtered temperature by coordinate and time
    public Temperature getTemperature(Coordinate coordinate, int timeOffset) {
        return this.getTemperatures(coordinate).get(timeOffset);
    }

    // Translate a locationCode into a coordinate
    public Optional<Coordinate> translateLocationCodeToCoordinates(String locationCode) {
        List<Location> locations = this.queryLocationEndpoint();

        return locations.stream()
            .filter(
                loc -> loc.getComparableAttributes().stream()
                    .anyMatch(locAtr -> locAtr.contains(locationCode))
            )
            .map(Coordinate::new)
            .findAny();
    }


}
