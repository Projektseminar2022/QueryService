package de.hbrs.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class Forecast {
    double longitude;
    double latitude;
    String expectedFor;
    String lastUpdate;
    Weather weather;

    @Data
    static
    class Weather {
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
        WeatherDetail[] weather;
        double pop;
        Rain rain;

        @Data
        static
        class WeatherDetail {
            int id;
            String main;
            String description;
            String icon;
        }

        @Data
        static
        class Rain {
            @JsonAlias({"1h"})
            double oneHour;
        }
    }

    public double getTemperature() {
        return weather.getTemp();
    }


}
