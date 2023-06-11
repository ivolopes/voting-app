package br.com.sicredi.voting.application.job;

import br.com.sicredi.voting.domain.entities.Session;
import br.com.sicredi.voting.domain.services.AgendaService;
import br.com.sicredi.voting.domain.services.VotingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Log4j2
public class SendVotingResultJob {
    private final VotingService service;
    private final AgendaService agendaService;

    @Scheduled(cron = "0 0/1 * * * *")
    public void sendResult() {
        log.info("Start sending voting results");
        List<Session> sessions = agendaService.findByAccountedAndFinishingTime(false, LocalDateTime.now())
                .flatMap(service::result)
                .collectList().block();

        if (sessions == null || sessions.isEmpty()) {
            log.info("There is no session results to send");
        }
    }
}
