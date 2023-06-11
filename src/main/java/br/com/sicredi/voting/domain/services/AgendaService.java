package br.com.sicredi.voting.domain.services;

import br.com.sicredi.voting.domain.dto.response.EntityCreatedResponse;
import br.com.sicredi.voting.domain.entities.Session;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface AgendaService {
    Mono<EntityCreatedResponse> save(String name);
    Mono<EntityCreatedResponse> createSession(String agendaId, String name);
    Flux<Session> findByAccountedAndFinishingTime(boolean accounted, LocalDateTime date);
}
