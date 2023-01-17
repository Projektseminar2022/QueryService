package de.hbrs;

import de.hbrs.model.Coordinate;
import de.hbrs.model.Forecast;
import de.hbrs.model.Location;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("ConstantConditions")
public class DataAcquisitionService {

    // Statics
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm");
    private static final String dataAcquisitionAPI = "http://localhost:5098";
    private static final String locationEndpoint  = "/Locations";
    private static final String weatherEndpoint   = "/WeatherForecast";

    // Endpoint Gates
    // WeatherForecast endpoint gate
    private List<Forecast> queryWeatherEndpoint(double longitude, double latitude) {
        String url = dataAcquisitionAPI + weatherEndpoint + "?longitude=" + longitude + "&latitude=" + latitude;
        RestTemplate restTemplate = new RestTemplate();
        return List.of(restTemplate.getForObject(url, Forecast[].class));
    }

    // Location endpoint gate
    private List<Location> queryLocationEndpoint() {
        String url = dataAcquisitionAPI + locationEndpoint + "/all";
        RestTemplate restTemplate = new RestTemplate();
        return List.of(restTemplate.getForObject(url, Location[].class));
    }

    // Get unfiltered forecasts by coordinate
    public List<Forecast> getForecasts(double longitude, double latitude) {
        return queryWeatherEndpoint(longitude, latitude);
    }

    // Get filtered forecast by coordinate and time
    public Forecast getForecast(double longitude, double latitude, String time) {
        LocalDateTime localDateTime = LocalDateTime.parse(time, DATE_TIME_FORMAT);
        return this.getForecasts(longitude, latitude).get(localDateTime.getHour());
    }

    // Get unfiltered temperatures by coordinate
    public List<Double> getTemperatures(double longitude, double latitude) {
        return this.getForecasts(longitude, latitude).stream().map(Forecast::getTemperature).collect(Collectors.toList());
    }

    // Get filtered temperature by coordinate and time
    public Double getTemperature(double longitude, double latitude, String time) {
        LocalDateTime localDateTime = LocalDateTime.parse(time, DATE_TIME_FORMAT);
        return this.getTemperatures(longitude, latitude).get(localDateTime.getHour());
    }

    // Translate a locationCode into a coordinate
    public Coordinate translateLocationCodeToCoordinates(String locationCode) {
        List<Location> locations = this.queryLocationEndpoint();

        for(Location location : locations) {
            for(String locationAttribute : location.getComparableAttributes()) {
                if(locationAttribute.contains(locationCode)) {
                    return new Coordinate(location.getLongitude(), location.getLatitude());
                }
            }
        }

        System.out.println("Found no location containing: '" + locationCode + "'.");
        return null;
    }


}
