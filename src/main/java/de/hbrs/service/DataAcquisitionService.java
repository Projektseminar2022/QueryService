package de.hbrs.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import de.hbrs.model.Coordinate;
import de.hbrs.model.Forecast;
import de.hbrs.model.Location;
import de.hbrs.model.Temperature;

@Service
public class DataAcquisitionService {

    // Statics
    // example with ngrok: 'https://fde1-2a02-908-620-85c0-500-89fb-61c3-8e31.eu.ngrok.io' -> http://localhost:8080
    private static final String DATA_ACQUISITION_API = "localhost:5098";
    private static final String LOCATION_ENDPOINT    = "Locations";
    private static final String WEATHER_ENDPOINT     = "WeatherForecast";

    private final WebClient webClient;

    public DataAcquisitionService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    // Endpoint Gates
    // WeatherForecast endpoint gate
    private Flux<Forecast> queryWeatherEndpoint(double longitude, double latitude) {

        Flux<Forecast> forecasts = webClient.get()
            .uri(
                uriBuilder -> uriBuilder
                    .path(DATA_ACQUISITION_API)
                    .pathSegment(WEATHER_ENDPOINT)
                    .queryParam("longitude", longitude)
                    .queryParam("latitude", latitude)
                    .build()
            )
            .retrieve()
            .bodyToFlux(Forecast.class);

        return forecasts;
    }

    // Location endpoint gate
    private Flux<Location> queryLocationEndpoint() {

        Flux<Location> locations = webClient.get()
            .uri(
                uriBuilder -> uriBuilder
                    .path(DATA_ACQUISITION_API)
                    .pathSegment(LOCATION_ENDPOINT, "all")
                    .build()
            )
            .retrieve()
            .bodyToFlux(Location.class);

        return locations;
    }

    // Get unfiltered forecasts by coordinate
    public Flux<Forecast> getForecasts(Coordinate coordinate) {
        return queryWeatherEndpoint(coordinate.longitude(), coordinate.latitude());
    }

    // Get filtered forecast by coordinate and time
    public Mono<Forecast> getForecast(Coordinate coordinate, int timeOffset) {
        return this.getForecasts(coordinate).elementAt(timeOffset);
    }

    // Get unfiltered temperatures by coordinate
    public Flux<Temperature> getTemperatures(Coordinate coordinate) {
        return this.getForecasts(coordinate)
                .map(Forecast::temperature)
                .map(Temperature::new);
    }

    // Get filtered temperature by coordinate and time
    public Mono<Temperature> getTemperature(Coordinate coordinate, int timeOffset) {
        return this.getTemperatures(coordinate).elementAt(timeOffset);
    }

    // Translate a locationCode into a coordinate
    public Mono<Coordinate> translateLocationCodeToCoordinates(String locationCode) {
        Flux<Location> locations = this.queryLocationEndpoint();

        Mono<Coordinate> location = locations.filter(
            loc -> loc.getComparableAttributes().stream()
                .anyMatch(locAtr -> locAtr.contains(locationCode))
        )
        .map(loc -> new Coordinate(loc))
        .next();

        return location;
    }

}
