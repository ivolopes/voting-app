package br.com.sicredi.voting.infrastructure.database.mongodb.repository;

import br.com.sicredi.voting.domain.entities.Vote;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface VoteSpringDataMongoRepository extends ReactiveMongoRepository<Vote, String> {
    Flux<Vote> findAllBySessionAndAffiliated(String sessionId, String affiliatedId);
    Mono<Long> countBySessionAndVote(String sessionId, boolean vote);
}
