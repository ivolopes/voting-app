package br.com.sicredi.voting.domain.repository;

import br.com.sicredi.voting.domain.entities.Session;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface SessionRepository {
    Mono<Session> save(Session session);
    Mono<Session> findById(String id);
    Flux<Session> findAllByAccountedAndFinishingTime(boolean accounted, LocalDateTime date);
}
