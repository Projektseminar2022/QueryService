package de.hbrs.controller;

import de.hbrs.DataAcquisitionService;
import de.hbrs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("/query")
public class QueryServiceController {

    // Injections
    private final DataAcquisitionService dataAcquisitionService;

    // Constructor
    @Autowired
    public QueryServiceController(DataAcquisitionService dataAcquisitionService) {
        this.dataAcquisitionService = dataAcquisitionService;
    }

    // Methods
    // Coordinate
    // Get forecasts by stating coordinate
    @GetMapping(
        path = "/forecasts-by-coordinate",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<List<Forecast>> forecastsByCoordinate(@RequestParam double longitude, @RequestParam double latitude) {
        return Mono.just(dataAcquisitionService.getForecasts(longitude, latitude));
    }

    // Get forecast by stating coordinate and time
    @GetMapping(
            path = "/forecast-by-coordinate-and-time",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Forecast> forecastByCoordinateAndTime(@RequestParam double longitude, @RequestParam double latitude, @RequestParam String time) {
        return Mono.just(dataAcquisitionService.getForecast(longitude, latitude, time));
    }

    // Get temperatures by stating coordinate
    @GetMapping(
            path = "/temperatures-by-coordinate",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<List<Double>> temperaturesByCoordinate(@RequestParam double longitude, @RequestParam double latitude) {
        return Mono.just(dataAcquisitionService.getTemperatures(longitude, latitude));
    }

    // Get temperature by coordinate and time
    @GetMapping(
            path = "/temperature-by-coordinate-and-time",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Double> temperatureByCoordinateAndTime(@RequestParam double longitude, @RequestParam double latitude, @RequestParam String time) {
        return Mono.just(dataAcquisitionService.getTemperature(longitude, latitude, time));
    }

    // LocationCode
    // Get forecasts by locationCode
    @GetMapping(
            path = "/forecasts-by-locationCode",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<List<Forecast>> forecastsByLocationCode(@RequestParam String locationCode) {
        Coordinate coordinate = dataAcquisitionService.translateLocationCodeToCoordinates(locationCode);

        // LocationCode did not match any location
        if(coordinate == null) { return Mono.empty();}

        return Mono.just(dataAcquisitionService.getForecasts(coordinate.longitude(), coordinate.latitude()));
    }

    // Get forecast by locationCode and time
    @GetMapping(
            path = "/forecast-by-locationCode-and-time",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Forecast> forecastByLocationCodeAndTime(@RequestParam String locationCode, @RequestParam String time) {
        Coordinate coordinate = dataAcquisitionService.translateLocationCodeToCoordinates(locationCode);

        // LocationCode did not match any location
        if(coordinate == null) {return Mono.empty();}

        return Mono.just(dataAcquisitionService.getForecast(coordinate.longitude(), coordinate.latitude(), time));
    }

    // Get temperature by locationCode
    @GetMapping(
            path = "/temperatures-by-locationCode",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<List<Double>> temperaturesByLocationCode(@RequestParam String locationCode) {
        Coordinate coordinate = dataAcquisitionService.translateLocationCodeToCoordinates(locationCode);

        // LocationCode did not match any location
        if(coordinate == null) { return Mono.empty();}

        return Mono.just(dataAcquisitionService.getTemperatures(coordinate.longitude(), coordinate.latitude()));
    }

    // Get temperature by locationCode and time
    @GetMapping(
            path = "/temperature-by-locationCode-and-time",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Double> temperatureByLocationCodeAndTime(@RequestParam String locationCode, @RequestParam String time) {
        Coordinate coordinate = dataAcquisitionService.translateLocationCodeToCoordinates(locationCode);

        // LocationCode did not match any location
        if(coordinate == null) { return Mono.empty();}

        return Mono.just(dataAcquisitionService.getTemperature(coordinate.longitude(), coordinate.latitude(), time));
    }

}
