package de.hbrs.controller;

// import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
// import de.hbrs.model.repositories.TemperatureRepository;

@RestController
@RequestMapping("/query")
public class QueryServiceController {

    // -------------------------------------------------------------------------
    // STATIC FIELDS

    // private static final
    // DateTimeFormatter DATE_TIME_FORMAT =
    //     DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm");

    // -------------------------------------------------------------------------
    // INSTANCE FIELDS

    // Autowired Services
    // private final TemperatureRepository temperatureRepository;

    // -------------------------------------------------------------------------
    // CONSTRUCTOR

    // Constructor with Dependency Injection
    // @Autowired
    // public QueryServiceController(TemperatureRepository temperatureRepository) {

        // this.temperatureRepository = temperatureRepository;
    // }

    // -------------------------------------------------------------------------
    // GET MAPPINGS

    // Find by stating a location and a timestamp
    @GetMapping(
        path = "/find-by-location-and-time"
        // consumes = MediaType.APPLICATION_JSON_VALUE,
        // produces = MediaType.APPLICATION_JSON_VALUE'
    )
    public Mono<Double> findByLocationAndTime(/* @RequestBody Location location, @RequestParam String time */) {

        return Mono.just(null);
        // return temperatureRepository.findByLocationAndTime(location, LocalDateTime.parse(time, DATE_TIME_FORMAT));
    }

    // Find by location and the current time
    @GetMapping(
        path = "/find-by-location-at-current-time"
        // consumes = MediaType.APPLICATION_JSON_VALUE
        // produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Double> findByLocationAtCurrentTime(/* @RequestBody Location location */) {

        return Mono.just(null);
        // return temperatureRepository.findByLocationAndTime(location, LocalDateTime.now());
    }

    // Find by stating a User that allows location and time retrieval
    @GetMapping(
        path = "/find-by-user"
        // consumes = MediaType.APPLICATION_JSON_VALUE,
        // produces = MediaType.APPLICATION_JSON_VALUE
        )
        public Mono<Double> findByUser(/* @RequestBody User user */) {

        return Mono.just(null);
        // return temperatureRepository.findByLocationAndTime(user.location(), user.time());
    }


    // -------------------------------------------------------------------------

}
