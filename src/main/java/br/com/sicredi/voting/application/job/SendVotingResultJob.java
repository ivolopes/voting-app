package br.com.sicredi.voting.application.job;

import br.com.sicredi.voting.domain.entities.Agenda;
import br.com.sicredi.voting.domain.services.AgendaService;
import br.com.sicredi.voting.domain.services.VotingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Job responsible for sending result to the queues
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class SendVotingResultJob {
    private final VotingService service;
    private final AgendaService agendaService;

    /**
     * The job is scheduled to run for each one minute
     */
    @Scheduled(cron = "0 0/1 * * * *")
    public void sendResult() {
        log.info("Start sending voting results");
        List<Agenda> agendas = agendaService
                .findByAccountedAndFinishingTime(false, LocalDateTime.now().minusMinutes(2))
                .flatMap(service::result)
                .collectList().block();

        if (agendas == null || agendas.isEmpty()) {
            log.info("There is no agenda results to send");
        }
    }
}
