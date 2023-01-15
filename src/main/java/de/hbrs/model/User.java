package de.hbrs.model;

import java.time.LocalDateTime;

public record User(
    Location location,
    LocalDateTime time
) { }
