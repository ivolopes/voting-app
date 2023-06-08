package br.com.sicredi.voting.infrastructure.database.mongodb.repository;

import br.com.sicredi.voting.domain.entities.Session;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionSpringDataMongoRepository extends ReactiveMongoRepository<Session, String> {
}
