package de.hbrs.model.repositories;

import java.time.LocalDateTime;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

import de.hbrs.model.Temperature;

public interface TemperatureRepository extends ReactiveCrudRepository<Temperature, Long> {

    Mono<Temperature> findByLocationAndTime(String location, LocalDateTime time);
}
