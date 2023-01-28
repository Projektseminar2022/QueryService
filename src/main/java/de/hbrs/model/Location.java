package de.hbrs.model;

import java.util.List;

public record Location(
    double longitude,
    double latitude,
    String display_name,
    String city_district,
    String city,
    String state,
    String country,
    String country_code,
    String language
) {

    public List<String> getComparableAttributes() {
        return List.of(this.display_name, this.city_district, this.city, this.state, this.country, this.country_code);
    }
}
