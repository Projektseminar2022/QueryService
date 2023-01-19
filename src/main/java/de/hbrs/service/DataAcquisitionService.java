package de.hbrs.service;

import de.hbrs.model.Coordinate;
import de.hbrs.model.Forecast;
import de.hbrs.model.Location;
import de.hbrs.model.Temperature;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DataAcquisitionService {

    // Statics
    // example with ngrok: 'https://fde1-2a02-908-620-85c0-500-89fb-61c3-8e31.eu.ngrok.io' -> http://localhost:8080
    private static final String DATA_ACQUISITION_API = "http://localhost:5098";
    private static final String LOCATION_ENDPOINT    = "Locations";
    private static final String WEATHER_ENDPOINT     = "WeatherForecast";

    // Endpoint Gates
    // WeatherForecast endpoint gate
    private List<Forecast> queryWeatherEndpoint(double longitude, double latitude) {

        WebClient webClient = WebClient.create();

        Forecast[] forecasts = webClient.get()
            .uri(
                uriBuilder -> uriBuilder
                    .path(DATA_ACQUISITION_API)
                    .pathSegment(WEATHER_ENDPOINT)
                    .queryParam("longitude", longitude)
                    .queryParam("latitude", latitude)
                    .build()
            )
            .retrieve()
            .bodyToMono(Forecast[].class)
            .block();

        return List.of(forecasts);
    }

    // Location endpoint gate
    private List<Location> queryLocationEndpoint() {

        WebClient webClient = WebClient.create();

        Location[] locations = webClient.get()
            .uri(
                uriBuilder -> uriBuilder
                    .path(DATA_ACQUISITION_API)
                    .pathSegment(LOCATION_ENDPOINT, "all")
                    .build()
            )
            .retrieve()
            .bodyToMono(Location[].class)
            .block();

        return List.of(locations);
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
