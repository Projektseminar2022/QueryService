package de.hbrs.controller;

import de.hbrs.model.Temperature;
import de.hbrs.model.User;
import de.hbrs.model.repositories.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class QueryServiceController {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm");

    // Autowired Services
    private final TemperatureRepository temperatureRepository;

    // Constructor Injection
    @Autowired
    public QueryServiceController(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    // Find by stating a location and a timestamp
    @GetMapping(path = "/find-by-location-and-time",
                produces = MediaType.APPLICATION_JSON_VALUE
                )
    public Mono<Temperature> findByLocationAndTime(@RequestParam String location, @RequestParam String time) {
        return temperatureRepository.findByLocationAndTime(location, LocalDateTime.parse(time, dateTimeFormatter));
    }

    // Find by location and the current time
    @GetMapping(path = "/find-by-location-at-current-time",
                produces = MediaType.APPLICATION_JSON_VALUE
                )
    public Mono<Temperature> findByLocationAtCurrentTime(@RequestParam String location) {
        return temperatureRepository.findByLocationAndTime(location, LocalDateTime.now());
    }

    // Find by stating a User that allows location and time retrieval
    @GetMapping(path = "/find-by-user",
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE
                )
    public Mono<Temperature> findByUser(@RequestBody User user) {
        return temperatureRepository.findByLocationAndTime(user.getLocation(), user.getTime());
    }
}
