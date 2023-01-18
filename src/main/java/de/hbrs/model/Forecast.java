package de.hbrs.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class Forecast {
    private double longitude;
    private double latitude;
    private String expectedFor;
    private String lastUpdate;
    private Weather weather;

    @Data
    static
    class Weather {
        private int dt;
        private double temp;
        private double feels_like;
        private int pressure;
        private int humidity;
        private double dew_point;
        private double uvi;
        private int clouds;
        private int visibility;
        private double wind_speed;
        private double wind_deg;
        private double wind_gust;
        private WeatherDetail[] weather;
        private double pop;
        private Rain rain;

        @Data
        static
        class WeatherDetail {
            private int id;
            private String main;
            private String description;
            private String icon;
        }

        @Data
        static
        class Rain {
            @JsonAlias({"1h"})
            private double oneHour;
        }
    }

    public double getTemperature() {
        return weather.getTemp();
    }


}
