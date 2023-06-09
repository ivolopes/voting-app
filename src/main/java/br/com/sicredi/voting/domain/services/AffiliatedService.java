package br.com.sicredi.voting.domain.services;

import br.com.sicredi.voting.domain.dto.response.EntityCreatedResponse;
import reactor.core.publisher.Mono;

public interface AffiliatedService {
    Mono<EntityCreatedResponse> save(String name, String cpf);
}
