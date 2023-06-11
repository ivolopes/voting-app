package br.com.sicredi.voting.domain.services;

import br.com.sicredi.voting.domain.dto.response.EntityCreatedResponse;
import br.com.sicredi.voting.domain.entities.Session;
import reactor.core.publisher.Mono;

public interface VotingService {
    Mono<Void> register(String sessionId, String affiliatedId, boolean vote);
    Mono<EntityCreatedResponse> save(String sessionId, String affiliatedId, boolean vote);
    Mono<Session> result(Session session);
}
