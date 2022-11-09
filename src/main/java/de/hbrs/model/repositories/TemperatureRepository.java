package de.hbrs.model.repositories;

import de.hbrs.model.Temperature;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface TemperatureRepository extends ReactiveCrudRepository<Temperature, Long> {

    Mono<Temperature> findByLocationAndTime(String location, LocalDateTime time);

}
