package br.com.sicredi.voting.domain.repository;

import br.com.sicredi.voting.domain.dto.response.VotingResultResponse;
import br.com.sicredi.voting.domain.entities.Vote;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VotingRepository {
    void register(String sessionId, String affiliatedId, boolean vote);
    Mono<Vote> save(Vote vote);
    Flux<Vote> findAllBySessionAndAffiliated(String sessionId, String affiliatedId);
    boolean validateCpf(String cpf);
    Mono<Long> countBySessionAndVote(String sessionId, boolean vote);
    void sendResult(VotingResultResponse result);
}
