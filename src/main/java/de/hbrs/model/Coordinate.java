package de.hbrs.model;

import lombok.AllArgsConstructor;
import lombok.Data;

// This class is used to enable longitude and latitude parsing for the location endpoint
@Data
@AllArgsConstructor
public class Coordinate {
        private double longitude;
        private double latitude;

        public Coordinate(Location location) {
                this.longitude = location.getLongitude();
                this.latitude = location.getLatitude();
        }
}
