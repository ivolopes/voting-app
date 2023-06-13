package br.com.sicredi.voting.infrastructure.database.mongodb.repository;

import br.com.sicredi.voting.domain.entities.Vote;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface VoteSpringDataMongoRepository extends ReactiveMongoRepository<Vote, String> {
    Flux<Vote> findAllByAgendaAndAffiliated(String agendaId, String affiliatedId);
    Mono<Long> countByAgendaAndVote(String agendaId, boolean vote);
}
