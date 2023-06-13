package br.com.sicredi.voting.domain.services;

import br.com.sicredi.voting.domain.dto.response.EntityCreatedResponse;
import br.com.sicredi.voting.domain.entities.Agenda;
import reactor.core.publisher.Mono;

public interface VotingService {
    Mono<Void> register(String agendaId, String affiliatedId, boolean vote);
    Mono<EntityCreatedResponse> save(String agendaId, String affiliatedId, boolean vote);
    Mono<Agenda> result(Agenda agenda);
}
