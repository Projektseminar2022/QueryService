package de.hbrs.service;

import de.hbrs.model.Coordinate;
import de.hbrs.model.Forecast;
import de.hbrs.model.Location;
import de.hbrs.model.Temperature;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DataAcquisitionService {

    // Statics
    // example with ngrok: 'https://fde1-2a02-908-620-85c0-500-89fb-61c3-8e31.eu.ngrok.io' -> http://localhost:8080
    private static final String DATA_ACQUISITION_API = "http://localhost:5098";
    private static final String LOCATION_ENDPOINT    = "Locations";
    private static final String WEATHER_ENDPOINT     = "WeatherForecast";

    // Endpoint Gates
    // WeatherForecast endpoint gate
    private Mono<List<Forecast>> queryWeatherEndpoint(double longitude, double latitude) {

        WebClient webClient = WebClient.create();

        Mono<List<Forecast>> forecasts = webClient.get()
            .uri(
                uriBuilder -> uriBuilder
                    .path(DATA_ACQUISITION_API)
                    .pathSegment(WEATHER_ENDPOINT)
                    .queryParam("longitude", longitude)
                    .queryParam("latitude", latitude)
                    .build()
            )
            .retrieve()
            .bodyToFlux(Forecast.class)
            .collectList();

        return forecasts;
    }

    // Location endpoint gate
    private Mono<List<Location>> queryLocationEndpoint() {

        WebClient webClient = WebClient.create();

        Mono<List<Location>> locations = webClient.get()
            .uri(
                uriBuilder -> uriBuilder
                    .path(DATA_ACQUISITION_API)
                    .pathSegment(LOCATION_ENDPOINT, "all")
                    .build()
            )
            .retrieve()
            .bodyToFlux(Location.class)
            .collectList();

        return locations;
    }

    // Get unfiltered forecasts by coordinate
    public Mono<List<Forecast>> getForecasts(Coordinate coordinate) {
        return queryWeatherEndpoint(coordinate.getLongitude(), coordinate.getLatitude());
    }

    // Get filtered forecast by coordinate and time
    public Mono<Forecast> getForecast(Coordinate coordinate, int timeOffset) {
        return this.getForecasts(coordinate).map(list -> list.get(timeOffset));
    }

    // Get unfiltered temperatures by coordinate
    public Mono<List<Temperature>> getTemperatures(Coordinate coordinate) {
        return this.getForecasts(coordinate)
            .map(list -> list.stream()
                .map(Forecast::getTemperature)
                .map(Temperature::new)
                .collect(Collectors.toList())
            );
    }

    // Get filtered temperature by coordinate and time
    public Mono<Temperature> getTemperature(Coordinate coordinate, int timeOffset) {
        return this.getTemperatures(coordinate).map(list -> list.get(timeOffset));
    }

    // Translate a locationCode into a coordinate
    public Mono<Coordinate> translateLocationCodeToCoordinates(String locationCode) {
        Mono<List<Location>> locations = this.queryLocationEndpoint();

        Mono<Coordinate> location = locations.map(
            list -> list.stream()
                .filter(
                    loc -> loc.getComparableAttributes().stream()
                        .anyMatch(locAtr -> locAtr.contains(locationCode))
                )
                .map(Coordinate::new)
                .findAny()
        ).flatMap(
            optional -> optional.map(Mono::just)
                .orElseGet(Mono::empty)
        );

        return location;
    }

}
