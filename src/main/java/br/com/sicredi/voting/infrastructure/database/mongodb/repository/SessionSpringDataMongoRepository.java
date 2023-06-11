package br.com.sicredi.voting.infrastructure.database.mongodb.repository;

import br.com.sicredi.voting.domain.entities.Session;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface SessionSpringDataMongoRepository extends ReactiveMongoRepository<Session, String> {
    Flux<Session> findAllByAccountedAndFinishingTimeBefore(boolean accounted, LocalDateTime date);
}
