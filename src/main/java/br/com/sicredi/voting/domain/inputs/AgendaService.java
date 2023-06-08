package br.com.sicredi.voting.domain.inputs;

import br.com.sicredi.voting.domain.dto.response.AgendaResponse;
import reactor.core.publisher.Mono;

public interface AgendaService {
    Mono<AgendaResponse> save(String name);
}
