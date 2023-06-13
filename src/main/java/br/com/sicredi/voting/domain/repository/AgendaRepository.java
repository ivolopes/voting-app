package br.com.sicredi.voting.domain.repository;

import br.com.sicredi.voting.domain.entities.Agenda;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface AgendaRepository {
    Mono<Boolean> existsById(String id);
    Flux<Agenda> findByName(String name);
    Mono<Agenda> save(Agenda agenda);
    Mono<Agenda> findById(String id);
    Flux<Agenda> findAllByAccountedAndFinishingTime(boolean accounted, LocalDateTime date);
    Mono<Boolean> existsByName(String name);
}
