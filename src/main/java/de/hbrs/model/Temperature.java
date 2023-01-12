package de.hbrs.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("temperatures")
public record Temperature(
    @Id UUID id,
    double temperature,
    String location,
    LocalDateTime time
) { }
