package br.com.sicredi.voting.domain.repository;

import br.com.sicredi.voting.domain.entities.Session;
import reactor.core.publisher.Mono;

public interface SessionRepository {
    Mono<Session> save(Session session);
}
