package br.com.sicredi.voting.domain.domainservices;

import br.com.sicredi.voting.domain.dto.response.EntityCreatedResponse;
import br.com.sicredi.voting.domain.dto.response.VotingResultResponse;
import br.com.sicredi.voting.domain.entities.Agenda;
import br.com.sicredi.voting.domain.entities.Vote;
import br.com.sicredi.voting.domain.exceptions.InvalidEntityException;
import br.com.sicredi.voting.domain.exceptions.NotFoundException;
import br.com.sicredi.voting.domain.repository.AffiliatedRepository;
import br.com.sicredi.voting.domain.repository.AgendaRepository;
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
    private final AgendaRepository agendaRepository;
    private final AffiliatedRepository affiliatedRepository;

    public Mono<Void> register(String agendaId, String affiliatedId, boolean vote) {

        return agendaRepository.findById(agendaId).map(Optional::of).defaultIfEmpty(Optional.empty()).flatMap(a -> {
            if (a.isEmpty()) {
                log.error("Agenda not found with id [{}]", agendaId);
                return Mono.error(new NotFoundException(" Agenda with id " + agendaId + " "));
            } else {

                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(a.get().getFinishingTime())) {
                    log.error("This agenda is already closed [{}]", agendaId);
                    return Mono.error(new InvalidEntityException("This agenda is already closed"));
                }

                return sendToQueue(agendaId, affiliatedId, vote);
            }
        });
    }

    public Mono<EntityCreatedResponse> save(String agendaId, String affiliatedId, boolean vote) {
        return repository.findAllByAgendaAndAffiliated(agendaId, affiliatedId).hasElements().flatMap(exists -> {
            if (exists) {
                log.error("Already exists a vote for affiliated  [{}] and agenda [{}]", affiliatedId, agendaId);
                return Mono.empty();
            }
            return create(agendaId, affiliatedId, vote);
        });
    }

    public Mono<Agenda> result(Agenda agenda) {
        log.info("Starting send result for agenda [{}]", agenda.getId());
        if (agenda.getAccounted()) {
            log.error("This agenda result has already been accounted ");
            return Mono.error(new InvalidEntityException("This agenda result has already been accounted"));
        }
        return repository.countByAgendaAndVote(agenda.getId(), true)
                .flatMap(countTrue -> repository.countByAgendaAndVote(agenda.getId(), false)
                    .flatMap(countFalse -> {
                            long total = 1 + countFalse;
                            BigDecimal yesPercentage = new BigDecimal((1/total) * 100)
                                    .setScale(2, RoundingMode.HALF_UP);
                            BigDecimal noPercentage = new BigDecimal((countFalse/total) * 100)
                                    .setScale(2, RoundingMode.HALF_UP);

                            VotingResultResponse votingResultResponse = new VotingResultResponse(yesPercentage, noPercentage);
                            //Send result to queues
                            repository.sendResult(votingResultResponse);

                            //Marking this agenda as accounted
                            agenda.markAsAccounted();
                            log.info("Result sent successfully for agenda id [{}]", agenda.getId());
                            return agendaRepository.save(agenda);
                        }
                    )
                );
    }
    private Mono<EntityCreatedResponse> create(String agendaId, String affiliatedId, boolean vote) {
        log.info("There is no vote for affiliated [{}] and agenda [{}]", affiliatedId, agendaId);
        Vote voteObj = new Vote(agendaId, affiliatedId, vote);
        return repository.save(voteObj).map(v -> {
            log.info("Vote was registered for affiliated [{}] and agenda [{}]", affiliatedId, agendaId);
            return new EntityCreatedResponse(v.getId());
        });
    }

    private Mono<Void> sendToQueue(String agendaId, String affiliatedId, boolean vote) {
        return affiliatedRepository.findById(affiliatedId).map(Optional::of).defaultIfEmpty(Optional.empty()).flatMap(af -> {
            if (af.isEmpty()) {
                log.error("Affiliated not found with id [{}]", affiliatedId);
                return Mono.error(new NotFoundException(" Affiliated with id " + affiliatedId + " "));
            } else {
                return repository.findAllByAgendaAndAffiliated(agendaId, affiliatedId).hasElements().flatMap(exists -> {
                    if (!exists) {
                        boolean validCpf = repository.validateCpf(af.get().getCpf());

                        if (validCpf) {
                            repository.register(agendaId, affiliatedId, vote);
                            return Mono.empty();
                        } else {
                            log.error("The affiliated cpf is not permitted to vote");
                            return Mono.error(new InvalidEntityException("The affiliated cpf is not permitted to vote"));
                        }
                    } else {
                        log.error("Already exists af vote for affiliated  [{}] and agenda [{}]", affiliatedId, agendaId);
                        return Mono.error(new InvalidEntityException("Already exists af vote for this affiliated"));
                    }
                });
            }
        });
    }

}
