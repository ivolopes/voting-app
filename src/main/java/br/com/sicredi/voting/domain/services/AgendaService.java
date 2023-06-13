package br.com.sicredi.voting.domain.services;

import br.com.sicredi.voting.domain.dto.response.EntityCreatedResponse;
import br.com.sicredi.voting.domain.entities.Agenda;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface AgendaService {
    Mono<EntityCreatedResponse> save(String name);
    Flux<Agenda> findByAccountedAndFinishingTime(boolean accounted, LocalDateTime date);
}
