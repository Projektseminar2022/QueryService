package de.hbrs.model;

// This class is used to enable longitude and latitude parsing for the location endpoint
public record Coordinate(
        double longitude,
        double latitude
) { }
