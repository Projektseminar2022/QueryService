package de.hbrs.model;

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

    class Weather {
        int id;
        String main;
        String description;
        String icon;
    }
}
