package br.com.sicredi.voting.infrastructure.adapters;

import br.com.sicredi.voting.domain.dto.response.VotingResultResponse;
import br.com.sicredi.voting.domain.entities.Vote;
import br.com.sicredi.voting.domain.repository.VotingRepository;
import br.com.sicredi.voting.infrastructure.database.mongodb.repository.VoteSpringDataMongoRepository;
import br.com.sicredi.voting.infrastructure.queue.rabbitmq.publisher.VotingPublisher;
import br.com.sicredi.voting.infrastructure.rest.CpfValidationApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class VotingRepositoryAdapter implements VotingRepository {

    private final VotingPublisher publisher;
    private final VoteSpringDataMongoRepository repository;
    private final CpfValidationApiClient cpfValidationApiClient;
    public void register(String agendaId, String affiliatedId, boolean vote) {
        publisher.registerVote(agendaId, affiliatedId, vote);
    }

    public Mono<Vote> save(Vote vote) {
        return repository.save(vote);
    }

    public Flux<Vote> findAllByAgendaAndAffiliated(String agendaId, String affiliatedId) {
        return repository.findAllByAgendaAndAffiliated(agendaId, affiliatedId);
    }

    public boolean validateCpf(String cpf) {
        return cpfValidationApiClient.validateCpf(cpf);
    }

    public Mono<Long> countByAgendaAndVote(String agendaId, boolean vote) {
        return repository.countByAgendaAndVote(agendaId, vote);
    }
    public void sendResult(VotingResultResponse result) {
        publisher.sendResult(result);
    }
}
