package br.com.sicredi.voting.infrastructure.adapters.database;

import br.com.sicredi.voting.domain.entities.Affiliated;
import br.com.sicredi.voting.domain.repository.AffiliatedRepository;
import br.com.sicredi.voting.infrastructure.database.mongodb.repository.AffiliatedSpringDataMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MongoDbAffiliatedRepository implements AffiliatedRepository {

    private final AffiliatedSpringDataMongoRepository repository;

    public Mono<Affiliated> save(Affiliated affiliated) {
        return repository.save(affiliated);
    }

    public Mono<Affiliated> findById(String id) {
        return repository.findById(id);
    }

    public Mono<Affiliated> findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }
}
