package br.com.sicredi.voting.infrastructure.adapters.database;

import br.com.sicredi.voting.domain.entities.Session;
import br.com.sicredi.voting.domain.repository.SessionRepository;
import br.com.sicredi.voting.infrastructure.database.mongodb.repository.SessionSpringDataMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MongoDbSessionRepository implements SessionRepository {

    private final SessionSpringDataMongoRepository repository;
    @Override
    public Mono<Session> save(Session session) {
        return repository.save(session);
    }
}
