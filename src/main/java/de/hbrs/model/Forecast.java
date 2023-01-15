package de.hbrs.model;

import lombok.Data;

@Data
public class Forecast {
    int dt;
    double temp;
    double feels_like;
    int pressure;
    int humidity;
    double dew_point;
    double uvi;
    int clouds;
    int visibility;
    double wind_speed;
    double wind_deg;
    double wind_gust;
    Weather[] weather;
    double pop;

    @SuppressWarnings("unused")
    static class Weather {
        int id;
        String main;
        String description;
        String icon;
    }
}
