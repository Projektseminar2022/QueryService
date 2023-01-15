package de.hbrs.model;

import java.time.LocalDateTime;

public record User(
    double longitude,
    double latitude,
    LocalDateTime time
) { }
