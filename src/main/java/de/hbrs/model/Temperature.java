package de.hbrs.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("temperature")
@Data
public class Temperature {

    @Id
    private UUID id;

    private float temperature;

    private String location;

    private LocalDateTime time;

}
