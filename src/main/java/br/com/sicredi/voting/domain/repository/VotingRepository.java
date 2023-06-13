package br.com.sicredi.voting.domain.repository;

import br.com.sicredi.voting.domain.dto.response.VotingResultResponse;
import br.com.sicredi.voting.domain.entities.Vote;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VotingRepository {
    void register(String agendaId, String affiliatedId, boolean vote);
    Mono<Vote> save(Vote vote);
    Flux<Vote> findAllByAgendaAndAffiliated(String agendaId, String affiliatedId);
    boolean validateCpf(String cpf);
    Mono<Long> countByAgendaAndVote(String agendaId, boolean vote);
    void sendResult(VotingResultResponse result);
}
