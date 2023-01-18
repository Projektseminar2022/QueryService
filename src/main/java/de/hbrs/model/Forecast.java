package de.hbrs.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Forecast(
    double longitude,
    double latitude,
    String expectedFor,
    String lastUpdate,
    Weather weather
) {
    
    public double temperature() {
        return weather.temp();
    }

    record Weather(
        int dt,
        double temp,
        double feels_like,
        int pressure,
        int humidity,
        double dew_point,
        double uvi,
        int clouds,
        int visibility,
        double wind_speed,
        double wind_deg,
        double wind_gust,
        WeatherDetail[] weather,
        double pop,
        Rain rain
    ) { }

    record WeatherDetail(
        int id,
        String main,
        String description,
        String icon
    ) { }

    record Rain(
        @JsonAlias({"1h"})
        double oneHour
    ) { }
}
