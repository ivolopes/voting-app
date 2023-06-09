package br.com.sicredi.voting.domain.repository;

import br.com.sicredi.voting.domain.entities.Affiliated;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface AffiliatedRepository {
    Mono<Affiliated> save(Affiliated affiliated);
    Mono<Affiliated> findById(String id);
    Mono<Affiliated> findByCpf(String cpf);
}
