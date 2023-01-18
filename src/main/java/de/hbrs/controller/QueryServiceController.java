package de.hbrs.controller;

import de.hbrs.model.*;
import de.hbrs.service.DataAcquisitionService;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/query")
public class QueryServiceController {

    // Injections
    private final DataAcquisitionService dataAcquisitionService;

    // Constructor
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
        return Mono.just(dataAcquisitionService.getForecasts(new Coordinate(longitude, latitude)));
    }

    // Get forecast by stating coordinate and time offset
    @GetMapping(
        path = "/forecast-by-coordinate-and-timeOffset",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Forecast> forecastByCoordinateAndTimeOffset(@RequestParam double longitude, @RequestParam double latitude, @RequestParam int timeOffset) {
        return Mono.just(dataAcquisitionService.getForecast(new Coordinate(longitude, latitude), timeOffset));
    }

    // Get temperatures by stating coordinate
    @GetMapping(
        path = "/temperatures-by-coordinate",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<List<Temperature>> temperaturesByCoordinate(@RequestParam double longitude, @RequestParam double latitude) {
        return Mono.just(dataAcquisitionService.getTemperatures(new Coordinate(longitude, latitude)));
    }

    // Get temperature by coordinate and time offset
    @GetMapping(
        path = "/temperature-by-coordinate-and-timeOffset",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Temperature> temperatureByCoordinateAndTimeOffset(@RequestParam double longitude, @RequestParam double latitude, @RequestParam int timeOffset) {
        return Mono.just(dataAcquisitionService.getTemperature(new Coordinate(longitude, latitude), timeOffset));
    }

    // LocationCode
    // Get forecasts by locationCode
    @GetMapping(
        path = "/forecasts-by-locationCode",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<List<Forecast>> forecastsByLocationCode(@RequestParam String locationCode) {
        Optional<Coordinate> coordinate = dataAcquisitionService.translateLocationCodeToCoordinates(locationCode);

        List<Forecast> forecasts = coordinate.map(dataAcquisitionService::getForecasts).orElseGet(List::of);

        return Mono.just(forecasts);
    }

    // Get forecast by locationCode and time offset
    @GetMapping(
            path = "/forecast-by-locationCode-and-timeOffset",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Forecast> forecastByLocationCodeAndTimeOffset(@RequestParam String locationCode, @RequestParam int timeOffset) {
        Optional<Coordinate> coordinate = dataAcquisitionService.translateLocationCodeToCoordinates(locationCode);

        Optional<Forecast> forecast = coordinate.map(coor -> dataAcquisitionService.getForecast(coor, timeOffset));

        return Mono.justOrEmpty(forecast);
    }

    // Get temperature by locationCode
    @GetMapping(
            path = "/temperatures-by-locationCode",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<List<Temperature>> temperaturesByLocationCode(@RequestParam String locationCode) {
        Optional<Coordinate> coordinate = dataAcquisitionService.translateLocationCodeToCoordinates(locationCode);

        List<Temperature> temperatures = coordinate.map(dataAcquisitionService::getTemperatures).orElseGet(List::of);

        return Mono.just(temperatures);
    }

    // Get temperature by locationCode and time offset
    @GetMapping(
            path = "/temperature-by-locationCode-and-timeOffset",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Temperature> temperatureByLocationCodeAndTimeOffset(@RequestParam String locationCode, @RequestParam int timeOffset) {
        Optional <Coordinate> coordinate = dataAcquisitionService.translateLocationCodeToCoordinates(locationCode);

        Optional<Temperature> temperature = coordinate.map(coord -> dataAcquisitionService.getTemperature(coord, timeOffset));

        return Mono.justOrEmpty(temperature);
    }

}
