package de.hbrs.model;

import lombok.Data;

import java.util.List;

@Data
public class Location {
    private double latitude;
    private double longitude;
    private String display_name;
    private String city_district;
    private String city;
    private String state;
    private String country;
    private String country_code;
    private String language;

    public List<String> getComparableAttributes() {
        return List.of(this.display_name, this.city_district, this.city, this.state, this.country, this.country_code);
    }
}
