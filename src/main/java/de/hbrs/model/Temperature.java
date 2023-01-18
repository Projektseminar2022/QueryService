package de.hbrs.model;

import lombok.Data;

@Data
public class Temperature {
    private double temperature;

    public Temperature(Double temperature) {
        this.temperature = temperature.doubleValue();
    }
}
