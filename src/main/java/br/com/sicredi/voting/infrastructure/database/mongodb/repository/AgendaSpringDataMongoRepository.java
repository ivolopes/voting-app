package br.com.sicredi.voting.infrastructure.database.mongodb.repository;

import br.com.sicredi.voting.domain.entities.Agenda;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface AgendaSpringDataMongoRepository extends ReactiveMongoRepository<Agenda, String> {
    Flux<Agenda> findByName(String name);
    Mono<Boolean> existsByName(String name);
    Flux<Agenda> findAllByAccountedAndFinishingTimeBefore(boolean accounted, LocalDateTime date);
}
