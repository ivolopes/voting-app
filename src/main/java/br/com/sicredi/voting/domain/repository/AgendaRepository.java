package br.com.sicredi.voting.domain.repository;

import br.com.sicredi.voting.domain.entities.Agenda;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AgendaRepository {
    Flux<Agenda> findByName(String name);
    Mono<Agenda> save(Agenda agenda);

    Mono<Boolean> existsByName(String name);
}