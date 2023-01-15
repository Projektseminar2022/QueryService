package de.hbrs.controller;

import de.hbrs.model.*;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/query")
public class QueryServiceController {

    // -------------------------------------------------------------------------
    // STATIC FIELDS

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm");

    private static final String dataAcquisitionAPI = "localhost:8080/data-acquisition";
    private static final String locationEndpoint  = "/location";
    private static final String weatherEndpoint   = "/weather";

    // -------------------------------------------------------------------------
    // MAIN DATA GATES
    @SuppressWarnings("ConstantConditions")
    private List<Forecast> queryWeatherEndpoint(double longitude, double latitude) {
        String url = dataAcquisitionAPI + weatherEndpoint + "?longitude=" + longitude + ",latitude=" + latitude;
        RestTemplate restTemplate = new RestTemplate();
        return List.of(restTemplate.getForObject(url, ForecastList.class).getForecasts());
    }

    @SuppressWarnings("ConstantConditions")
    private List<Location> queryLocationEndpoint() {
        String url = dataAcquisitionAPI + locationEndpoint + "/all";
        RestTemplate restTemplate = new RestTemplate();
        return List.of(restTemplate.getForObject(url, LocationList.class).getLocations());
    }

    // -------------------------------------------------------------------------
    // PARSER METHODS
    // FORECAST
    private List<Forecast> getForecasts(double longitude, double latitude) {
        return queryWeatherEndpoint(longitude, latitude);
    }

    // Filter the forecasts for location and time
    private Forecast getForecast(double longitude, double latitude, String time) {
        LocalDateTime localDateTime = LocalDateTime.parse(time, DATE_TIME_FORMAT);
        return this.getForecasts(longitude, latitude).get(localDateTime.getHour());
    }

    private List<Double> getTemperatures(double longitude, double latitude) {
        return this.getForecasts(longitude, latitude).stream().map(Forecast::getTemp).collect(Collectors.toList());
    }

    private Double getTemperature(double longitude, double latitude, String time) {
        LocalDateTime localDateTime = LocalDateTime.parse(time, DATE_TIME_FORMAT);
        return this.getTemperatures(longitude, latitude).get(localDateTime.getHour());
    }

    // -------------------------------------------------------------------------
    // LOCATION
    private Coordinate translateLocationCodeToCoordinates(String locationCode) {
        List<Location> locations = this.queryLocationEndpoint();

        for(Location location : locations) {
            for(String locationAttribute : location.getComparableAttributes()) {
                if(locationAttribute.contains(locationCode)) {
                    return new Coordinate(location.getLongitude(), location.getLatitude());
                }
            }
        }

        return null;
    }


    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // GET MAPPINGS

    // COORDINATES

    // FORECASTS

    // Find forecasts by stating a location
    @GetMapping(
        path = "/find-forecasts-by-coordinates",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<List<Forecast>> findForecastsByCoordinates(@RequestParam double longitude, @RequestParam double latitude) {
        return Mono.just(this.getForecasts(longitude, latitude));
    }

    // Find forecast by stating location and time
    @GetMapping(
            path = "/find-forecast-by-coordinates-and-time",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Forecast> findForecastByCoordinatesAndTime(@RequestParam double longitude, @RequestParam double latitude, @RequestParam String time) {
        return Mono.just(this.getForecast(longitude, latitude, time));
    }

    // -------------------------------------------------------------------------
    // TEMPERATURES

    // Find temperatures by stating a location
    @GetMapping(
            path = "/find-temperatures-by-coordinates",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<List<Double>> findTemperaturesByCoordinates(@RequestParam double longitude, @RequestParam double latitude) {
        return Mono.just(this.getTemperatures(longitude, latitude));
    }

    // Find temperature by stating location and time
    @GetMapping(
            path = "/find-temperature-by-coordinate-and-time",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Double> findTemperatureByCoordinatesAndTime(@RequestParam double longitude, @RequestParam double latitude, @RequestParam String time) {
        return Mono.just(this.getTemperature(longitude, latitude, time));
    }

    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // LOCATION

    // FORECASTS

    @GetMapping(
            path = "/find-forecasts-by-location",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<List<Forecast>> findForecastsByLocation(@RequestParam String locationCode) {
        Coordinate coordinate = this.translateLocationCodeToCoordinates(locationCode);

        // LocationCode did not match for any location
        if(coordinate == null) { return Mono.empty();}

        return Mono.just(this.getForecasts(coordinate.longitude(), coordinate.latitude()));
    }

    @GetMapping(
            path = "/find-forecast-by-location-and-time",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Forecast> findForecastByLocationAndTime(@RequestParam String locationCode, @RequestParam String time) {
        Coordinate coordinate = this.translateLocationCodeToCoordinates(locationCode);

        // LocationCode did not match for any location
        if(coordinate == null) {return Mono.empty();}

        return Mono.just(this.getForecast(coordinate.longitude(), coordinate.latitude(), time));
    }

    @GetMapping(
            path = "/find-temperatures-by-location",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<List<Double>> findTemperaturesByLocation(@RequestParam String locationCode) {
        Coordinate coordinate = this.translateLocationCodeToCoordinates(locationCode);

        // LocationCode did not match for any location
        if(coordinate == null) { return Mono.empty();}

        return Mono.just(this.getTemperatures(coordinate.longitude(), coordinate.latitude()));
    }

    @GetMapping(
            path = "/find-temperature-by-location-and-time",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Double> findTemperatureByLocationAndTime(@RequestParam String locationCode, @RequestParam String time) {
        Coordinate coordinate = this.translateLocationCodeToCoordinates(locationCode);

        // LocationCode did not match for any location
        if(coordinate == null) { return Mono.empty();}

        return Mono.just(this.getTemperature(coordinate.longitude(), coordinate.latitude(), time));
    }

    // -------------------------------------------------------------------------

}
