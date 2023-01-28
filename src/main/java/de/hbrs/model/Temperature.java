package de.hbrs.model;

public record Temperature(double temperature) {

    public Temperature(Double temperature) {
        this(temperature.doubleValue());
    }
}
