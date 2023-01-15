package de.hbrs.controller;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import de.hbrs.model.Forecast;
import de.hbrs.model.ForecastList;
import de.hbrs.model.User;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/query")
public class QueryServiceController {

    // -------------------------------------------------------------------------
    // STATIC FIELDS

    // private static final
    // DateTimeFormatter DATE_TIME_FORMAT =
    //     DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm");

    private static final String dataAquisitionAPI = "localhost:8080/data-aquisition";
    private static final String locationEndpoint  = "/location";
    private static final String weatherEndpoint   = "/weather";

    // -------------------------------------------------------------------------
    private ForecastList callAPI1(double longitude, double latitude) {
        String url = dataAquisitionAPI + weatherEndpoint + "?longitude=" + longitude + ",latitude=" + latitude;

        RestTemplate restTemplate = new RestTemplate();
        ForecastList list = restTemplate.getForObject(url, ForecastList.class);

        return list;
    }

    // -------------------------------------------------------------------------
    // GET MAPPINGS

    // Find by stating a location and a timestamp ...and-time/?/location
    @GetMapping(
        path = "/find-forcast-by-coordinates",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<String> findForecastByCoordinates(@RequestParam double longitude, @RequestParam double latitude) {
        return Mono.just(this.callAPI1(longitude, latitude));
    }

    // Find by stating a location and a timestamp ...and-time/?/location
    @GetMapping(
        path = "/find-temperature-by-coordinates",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<String> findTemperatureByCoordinates(@RequestParam double longitude, @RequestParam double latitude) {

        this.callAPI1(longitude, latitude);

        return str;
    }

    // Find by location and the current time
    @GetMapping(
        path = "/find-by-location-at-current-time",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Double> findByLocationAtCurrentTime(@RequestParam double longitude, @RequestParam double latitude) {

        return Mono.just(null);
    }

    // Find by stating a User that allows location and time retrieval
    @GetMapping(
        path = "/find-by-user",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<Double> findByUser(@RequestBody User user) {

        return Mono.just(null);
    }

    @GetMapping(
        path = "/find-by-city"
    )
    public Mono<Double> findByCity(@RequestParam String city) {

        
        return Mono.just(null);
    }


    // -------------------------------------------------------------------------

}
