package de.hbrs.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import de.hbrs.model.Temperature;
import de.hbrs.model.User;
import de.hbrs.model.repositories.TemperatureRepository;

@RestController
public class QueryServiceController {

    // -------------------------------------------------------------------------
    // STATIC FIELDS
    
    private static final
    DateTimeFormatter DATE_TIME_FORMAT =
        DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm");

    // -------------------------------------------------------------------------
    // INSTANCE FIELDS
    
    // Autowired Services
    private final TemperatureRepository temperatureRepository;

    // -------------------------------------------------------------------------
    // CONSTRUCTOR

    // Constructor with Dependency Injection
    @Autowired
    public QueryServiceController(TemperatureRepository temperatureRepository) {

        this.temperatureRepository = temperatureRepository;
    }

    // -------------------------------------------------------------------------
    // GET MAPPINGS

    // Find by stating a location and a timestamp
    @GetMapping(
        path = "/find-by-location-and-time",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Temperature> findByLocationAndTime(@RequestParam String location, @RequestParam String time) {

        return temperatureRepository.findByLocationAndTime(location, LocalDateTime.parse(time, DATE_TIME_FORMAT));
    }

    // Find by location and the current time
    @GetMapping(
        path = "/find-by-location-at-current-time",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Temperature> findByLocationAtCurrentTime(@RequestParam String location) {

        return temperatureRepository.findByLocationAndTime(location, LocalDateTime.now());
    }

    // Find by stating a User that allows location and time retrieval
    @GetMapping(
        path = "/find-by-user",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Temperature> findByUser(@RequestBody User user) {

        return temperatureRepository.findByLocationAndTime(user.location(), user.time());
    }
}
