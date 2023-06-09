package br.com.sicredi.voting.domain.services;

import br.com.sicredi.voting.domain.dto.response.EntityCreatedResponse;
import reactor.core.publisher.Mono;

public interface AgendaService {
    Mono<EntityCreatedResponse> save(String name);
    Mono<EntityCreatedResponse> createSession(String agendaId, String name);
}
