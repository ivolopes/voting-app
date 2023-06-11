package br.com.sicredi.voting.infrastructure.adapters;

import br.com.sicredi.voting.domain.entities.Session;
import br.com.sicredi.voting.domain.repository.SessionRepository;
import br.com.sicredi.voting.infrastructure.database.mongodb.repository.SessionSpringDataMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SessionRepositoryAdapter implements SessionRepository {

    private final SessionSpringDataMongoRepository repository;
    @Override
    public Mono<Session> save(Session session) {
        return repository.save(session);
    }

    @Override
    public Mono<Session> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Session> findAllByAccountedAndFinishingTime(boolean accounted, LocalDateTime date) {
        return repository.findAllByAccountedAndFinishingTimeBefore(accounted, date);
    }
}
