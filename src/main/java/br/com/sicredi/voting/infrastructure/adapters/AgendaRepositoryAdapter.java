package br.com.sicredi.voting.infrastructure.adapters;

import br.com.sicredi.voting.domain.entities.Agenda;
import br.com.sicredi.voting.domain.repository.AgendaRepository;
import br.com.sicredi.voting.infrastructure.database.mongodb.repository.AgendaSpringDataMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AgendaRepositoryAdapter implements AgendaRepository {

    private final AgendaSpringDataMongoRepository repository;

    public Mono<Boolean> existsById(String id) {
        return repository.existsById(id);
    }

    public Flux<Agenda> findByName(String name) {
        return repository.findByName(name);
    }

    public Mono<Agenda> save(Agenda agenda) {
        return repository.save(agenda);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return repository.existsByName(name);
    }
}
