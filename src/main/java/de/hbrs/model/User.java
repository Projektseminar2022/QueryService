package de.hbrs.model;

import java.time.LocalDateTime;

public record User(
    String location,
    LocalDateTime time
) { }
