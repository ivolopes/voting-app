package br.com.sicredi.voting.domain.domainservices;

import br.com.sicredi.voting.domain.dto.response.EntityCreatedResponse;
import br.com.sicredi.voting.domain.dto.response.VotingResultResponse;
import br.com.sicredi.voting.domain.entities.Session;
import br.com.sicredi.voting.domain.entities.Vote;
import br.com.sicredi.voting.domain.exceptions.InvalidEntityException;
import br.com.sicredi.voting.domain.exceptions.NotFoundException;
import br.com.sicredi.voting.domain.repository.AffiliatedRepository;
import br.com.sicredi.voting.domain.repository.SessionRepository;
import br.com.sicredi.voting.domain.repository.VotingRepository;
import br.com.sicredi.voting.domain.services.VotingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class VotingDomainService implements VotingService {
    private final VotingRepository repository;
    private final SessionRepository sessionRepository;
    private final AffiliatedRepository affiliatedRepository;

    public Mono<Void> register(String sessionId, String affiliatedId, boolean vote) {

        return sessionRepository.findById(sessionId).map(Optional::of).defaultIfEmpty(Optional.empty()).flatMap(s -> {
            if (s.isEmpty()) {
                log.error("Session not found with id [{}]", sessionId);
                return Mono.error(new NotFoundException(" Session with id " + sessionId + " "));
            } else {

                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(s.get().getFinishingTime())) {
                    log.error("This session is already closed [{}]", sessionId);
                    return Mono.error(new InvalidEntityException("This session is already closed"));
                }

                return affiliatedRepository.findById(affiliatedId).map(Optional::of).defaultIfEmpty(Optional.empty()).flatMap(a -> {
                    if (a.isEmpty()) {
                        log.error("Affiliated not found with id [{}]", affiliatedId);
                        return Mono.error(new NotFoundException(" Affiliated with id " + affiliatedId + " "));
                    } else {
                        return repository.findAllBySessionAndAffiliated(sessionId, affiliatedId).hasElements().flatMap(exists -> {
                            if (!exists) {
                                boolean validCpf = repository.validateCpf(a.get().getCpf());

                                if (validCpf) {
                                    repository.register(sessionId, affiliatedId, vote);
                                    return Mono.empty();
                                } else {
                                    log.error("The affiliated cpf is not permitted to vote");
                                    return Mono.error(new InvalidEntityException("The affiliated cpf is not permitted to vote"));
                                }
                            } else {
                                log.error("Already exists a vote for affiliated  [{}] and session [{}]", affiliatedId, sessionId);
                                return Mono.error(new InvalidEntityException("Already exists a vote for this affiliated"));
                            }
                        });
                    }
                });
            }
        });
    }

    public Mono<EntityCreatedResponse> save(String sessionId, String affiliatedId, boolean vote) {
        return repository.findAllBySessionAndAffiliated(sessionId, affiliatedId).hasElements().flatMap(exists -> {
            if (!exists) {
                return create(sessionId, affiliatedId, vote);
            } else {
                log.error("Already exists a vote for affiliated  [{}] and session [{}]", affiliatedId, sessionId);
                return Mono.empty();
            }
        });
    }

    public Mono<Session> result(Session session) {
        log.info("Starting send result for session [{}]", session.getId());
        if (session.getAccounted()) {
            log.error("This session result has already been accounted ");
            return Mono.error(new InvalidEntityException("This session result has already been accounted"));
        }
        return repository.countBySessionAndVote(session.getId(), true)
                .flatMap(countTrue -> repository.countBySessionAndVote(session.getId(), false)
                    .flatMap(countFalse -> {
                            long total = 1 + countFalse;
                            BigDecimal yesPercentage = new BigDecimal((1/total) * 100)
                                    .setScale(2, RoundingMode.HALF_UP);
                            BigDecimal noPercentage = new BigDecimal((countFalse/total) * 100)
                                    .setScale(2, RoundingMode.HALF_UP);

                            VotingResultResponse votingResultResponse = new VotingResultResponse(yesPercentage, noPercentage);
                            //Send result to queues
                            repository.sendResult(votingResultResponse);

                            //Marking this session as accounted
                            session.markAsAccounted();
                            log.info("Result sent successfully for session id [{}]", session.getId());
                            return sessionRepository.save(session);
                        }
                    )
                );
    }
    private Mono<EntityCreatedResponse> create(String sessionId, String affiliatedId, boolean vote) {
        log.info("There is no vote for affiliated [{}] and session [{}]", affiliatedId, sessionId);
        Vote voteObj = new Vote(sessionId, affiliatedId, vote);
        return repository.save(voteObj).map(v -> {
            log.info("Vote was registered for affiliated [{}] and session [{}]", affiliatedId, sessionId);
            return new EntityCreatedResponse(v.getId());
        });
    }

}
