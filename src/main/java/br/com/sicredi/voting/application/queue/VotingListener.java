package br.com.sicredi.voting.application.queue;

import br.com.sicredi.voting.domain.dto.request.VotingRequest;
import br.com.sicredi.voting.domain.services.VotingService;
import br.com.sicredi.voting.infrastructure.queue.rabbitmq.config.RabbitmqConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Listener responsible for getting votes in the RabbitMq and save
 *
 * @author ivo
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class VotingListener {

    private final VotingService service;
    @RabbitListener(queues = RabbitmqConstants.VOTING_QUEUE)
    public void receiveVoting(VotingRequest message) {
        log.info("Start saving vote for affiliated [{}] and session [{}]",
                message.getAffiliatedId(), message.getSessionId());
        service.save(message.getSessionId(), message.getAffiliatedId(), message.isVote()).block();
    }
}
