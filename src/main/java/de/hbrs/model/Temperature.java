package de.hbrs.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("temperatures")
public record Temperature(
    @Id UUID id,
    float temperature, // TODO: why float? Does the API only provide float precision? If not -> double
    String location,
    LocalDateTime time
) { }
