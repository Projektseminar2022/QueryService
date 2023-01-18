package de.hbrs.model;

// This class is used to enable longitude and latitude parsing for the location endpoint
public record Coordinates(
    double longitude,
    double latitude
) {
    public Coordinates(Location location) {
        this(location.longitude(), location.latitude());
    }
}
