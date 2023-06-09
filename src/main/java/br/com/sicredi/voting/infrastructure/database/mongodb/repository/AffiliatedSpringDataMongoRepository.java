package br.com.sicredi.voting.infrastructure.database.mongodb.repository;

import br.com.sicredi.voting.domain.entities.Affiliated;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AffiliatedSpringDataMongoRepository extends ReactiveMongoRepository<Affiliated, String> {
    Mono<Affiliated> findByCpf(String cpf);
}
